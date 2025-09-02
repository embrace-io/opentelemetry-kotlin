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
internal class BatchSpanProcessorTest {

    private val fakeSpan = FakeReadWriteSpan()
    private val fakeContext = FakeContext()
    private lateinit var errorHandler: FakeSdkErrorHandler
    private lateinit var exporter: FakeSpanExporter

    @BeforeTest
    fun setUp() {
        errorHandler = FakeSdkErrorHandler()
        exporter = FakeSpanExporter()
    }

    @Test
    fun testOnStartDoesNothing() {
        val processor = BatchSpanProcessor(exporter, errorHandler)
        processor.onStart(fakeSpan, fakeContext)
        
        assertTrue(exporter.exports.isEmpty())
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testOnEndBatchesSpans() {
        val config = BatchSpanProcessorConfig(maxBatchSize = 3)
        val processor = BatchSpanProcessor(exporter, errorHandler, config)
        
        // Add 2 spans - should not export yet
        processor.onEnd(fakeSpan)
        processor.onEnd(fakeSpan)
        
        assertTrue(exporter.exports.isEmpty())
        assertFalse(errorHandler.hasErrors())
        
        // Add 3rd span - should trigger export
        processor.onEnd(fakeSpan)
        
        assertEquals(3, exporter.exports.size)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testForceFlushExportsRemaining() {
        val config = BatchSpanProcessorConfig(maxBatchSize = 5)
        val processor = BatchSpanProcessor(exporter, errorHandler, config)
        
        // Add 2 spans - should not export yet
        processor.onEnd(fakeSpan)
        processor.onEnd(fakeSpan)
        
        assertTrue(exporter.exports.isEmpty())
        
        // Force flush should export remaining spans
        val result = processor.forceFlush()
        
        assertEquals(OperationResultCode.Success, result)
        assertEquals(2, exporter.exports.size)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testShutdownExportsRemaining() {
        val config = BatchSpanProcessorConfig(maxBatchSize = 5)
        val processor = BatchSpanProcessor(exporter, errorHandler, config)
        
        // Add 1 span
        processor.onEnd(fakeSpan)
        
        assertTrue(exporter.exports.isEmpty())
        
        // Shutdown should export remaining spans
        val result = processor.shutdown()
        
        assertEquals(OperationResultCode.Success, result)
        assertEquals(1, exporter.exports.size)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testQueueOverflow() {
        val config = BatchSpanProcessorConfig(maxBatchSize = 10, maxQueueSize = 2)
        val processor = BatchSpanProcessor(exporter, errorHandler, config)
        
        // Add 2 spans - should be buffered
        processor.onEnd(fakeSpan)
        processor.onEnd(fakeSpan)
        
        assertFalse(errorHandler.hasErrors())
        
        // Add 3rd span - should be dropped due to queue overflow
        processor.onEnd(fakeSpan)
        
        assertTrue(errorHandler.hasErrors())
        assertTrue(exporter.exports.isEmpty()) // Still no export since maxBatchSize not reached
    }

    @Test
    fun testOnEndAfterShutdown() {
        val processor = BatchSpanProcessor(exporter, errorHandler)
        
        processor.shutdown()
        processor.onEnd(fakeSpan)
        
        // Should not add span after shutdown - exports will only contain shutdown batch (if any)
        // Since no spans were added before shutdown, exports should be empty
        assertTrue(exporter.exports.isEmpty())
    }

    @Test
    fun testForceFlushAfterShutdown() {
        val processor = BatchSpanProcessor(exporter, errorHandler)
        
        processor.shutdown()
        val result = processor.forceFlush()
        
        assertEquals(OperationResultCode.Success, result)
    }

    @Test
    fun testShutdownMultipleTimes() {
        val processor = BatchSpanProcessor(exporter, errorHandler)
        
        val result1 = processor.shutdown()
        val result2 = processor.shutdown()
        
        assertEquals(OperationResultCode.Success, result1)
        assertEquals(OperationResultCode.Success, result2)
    }

    @Test
    fun testExportException() {
        exporter = FakeSpanExporter(exportReturnValue = { throw RuntimeException("Export failed") })
        val processor = BatchSpanProcessor(exporter, errorHandler)
        
        processor.onEnd(fakeSpan)
        
        // Exception should be caught and logged
        assertTrue(errorHandler.hasErrors())
    }

    @Test
    fun testBatchExportFailure() {
        val config = BatchSpanProcessorConfig(maxBatchSize = 1)
        exporter = FakeSpanExporter(exportReturnValue = { OperationResultCode.Failure })
        val processor = BatchSpanProcessor(exporter, errorHandler, config)
        
        processor.onEnd(fakeSpan)
        
        assertEquals(1, exporter.exports.size)
        assertTrue(errorHandler.hasErrors())
    }

    @Test
    fun testIsStartRequiredReturnsFalse() {
        val processor = BatchSpanProcessor(exporter, errorHandler)
        assertFalse(processor.isStartRequired())
    }

    @Test
    fun testIsEndRequiredReturnsTrue() {
        val processor = BatchSpanProcessor(exporter, errorHandler)
        assertTrue(processor.isEndRequired())
    }
}