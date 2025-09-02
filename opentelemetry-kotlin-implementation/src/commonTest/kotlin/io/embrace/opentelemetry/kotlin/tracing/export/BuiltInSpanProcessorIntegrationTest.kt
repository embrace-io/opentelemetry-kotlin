package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.FakeReadWriteSpan
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class BuiltInSpanProcessorIntegrationTest {

    private val fakeSpan = FakeReadWriteSpan()
    private val fakeContext = FakeContext()
    private lateinit var errorHandler: FakeSdkErrorHandler
    private lateinit var exporter1: FakeSpanExporter
    private lateinit var exporter2: FakeSpanExporter

    @BeforeTest
    fun setUp() {
        errorHandler = FakeSdkErrorHandler()
        exporter1 = FakeSpanExporter()
        exporter2 = FakeSpanExporter()
    }

    @Test
    fun testCompositeWithBuiltInProcessors() {
        // Create a composite processor with built-in processors
        val noopProcessor = NoopSpanProcessor
        val simpleProcessor = SimpleSpanProcessor(exporter1, errorHandler)
        val batchProcessor = BatchSpanProcessor(exporter2, errorHandler, BatchSpanProcessorConfig(maxBatchSize = 2))
        
        val composite = CompositeSpanProcessor(
            listOf(noopProcessor, simpleProcessor, batchProcessor),
            errorHandler
        )

        // Test onEnd - should trigger simple processor immediately, batch processor after 2 spans
        composite.onEnd(fakeSpan)
        
        // Simple processor should have exported immediately
        assertEquals(1, exporter1.exports.size)
        // Batch processor should not have exported yet
        assertTrue(exporter2.exports.isEmpty())
        
        // Add second span to trigger batch export
        composite.onEnd(fakeSpan)
        
        // Simple processor should have exported both spans
        assertEquals(2, exporter1.exports.size)
        // Batch processor should now have exported the batch
        assertEquals(2, exporter2.exports.size)
        
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testCompositeWithMixedRequirements() {
        // Test that composite correctly handles different isStartRequired/isEndRequired values
        val noopProcessor = NoopSpanProcessor // start=false, end=false
        val simpleProcessor = SimpleSpanProcessor(exporter1, errorHandler) // start=false, end=true
        val batchProcessor = BatchSpanProcessor(exporter2, errorHandler) // start=false, end=true
        
        val composite = CompositeSpanProcessor(
            listOf(noopProcessor, simpleProcessor, batchProcessor),
            errorHandler
        )

        // Composite should require start and end since at least one processor requires each
        assertTrue(composite.isStartRequired())
        assertTrue(composite.isEndRequired())
    }

    @Test
    fun testShutdownPropagation() {
        val simpleProcessor = SimpleSpanProcessor(exporter1, errorHandler)
        val batchProcessor = BatchSpanProcessor(exporter2, errorHandler)
        
        val composite = CompositeSpanProcessor(
            listOf(simpleProcessor, batchProcessor),
            errorHandler
        )

        val result = composite.shutdown()
        
        assertEquals(OperationResultCode.Success, result)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testForceFlushPropagation() {
        val simpleProcessor = SimpleSpanProcessor(exporter1, errorHandler)
        val batchProcessor = BatchSpanProcessor(exporter2, errorHandler, BatchSpanProcessorConfig(maxBatchSize = 5))
        
        val composite = CompositeSpanProcessor(
            listOf(simpleProcessor, batchProcessor),
            errorHandler
        )

        // Add a span to the batch processor (but not enough to trigger auto-export)
        composite.onEnd(fakeSpan)
        
        // Simple processor should have exported, batch should not
        assertEquals(1, exporter1.exports.size)
        assertTrue(exporter2.exports.isEmpty())
        
        // Force flush should export the pending batch
        val result = composite.forceFlush()
        
        assertEquals(OperationResultCode.Success, result)
        assertEquals(1, exporter1.exports.size) // Simple processor export count unchanged
        assertEquals(1, exporter2.exports.size) // Batch processor should have exported
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testErrorHandlingInComposite() {
        // Create processors where one will fail
        val failingExporter = FakeSpanExporter(exportReturnValue = { OperationResultCode.Failure })
        val successExporter = FakeSpanExporter()
        
        val failingProcessor = SimpleSpanProcessor(failingExporter, errorHandler)
        val successProcessor = SimpleSpanProcessor(successExporter, errorHandler)
        
        val composite = CompositeSpanProcessor(
            listOf(failingProcessor, successProcessor),
            errorHandler
        )

        composite.onEnd(fakeSpan)
        
        // Both processors should have attempted export
        assertEquals(1, failingExporter.exports.size)
        assertEquals(1, successExporter.exports.size)
        
        // Error handler should have captured the failure from the first processor
        assertTrue(errorHandler.hasErrors())
    }
}