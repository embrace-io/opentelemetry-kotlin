package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadWriteLogRecord
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Integration tests that verify all built-in processors work together with the composite processor.
 */
@OptIn(ExperimentalApi::class)
internal class BuiltInProcessorsIntegrationTest {

    private val fakeContext = FakeContext()

    @Test
    fun testNoopProcessorWithComposite() {
        val noopProcessor = NoopLogRecordProcessor.INSTANCE
        val fakeExporter = FakeLogRecordExporter()
        val simpleProcessor = SimpleLogRecordProcessor(fakeExporter)
        
        val compositeProcessor = CompositeLogRecordProcessor(
            listOf(noopProcessor, simpleProcessor),
            io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler()
        )
        
        compositeProcessor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        // Noop does nothing, simple exports immediately
        assertEquals(1, fakeExporter.logs.size)
        assertEquals(OperationResultCode.Success, compositeProcessor.forceFlush())
        assertEquals(OperationResultCode.Success, compositeProcessor.shutdown())
    }

    @Test
    fun testSimpleAndBatchProcessorsTogether() {
        val simpleExporter = FakeLogRecordExporter()
        val batchExporter = FakeLogRecordExporter()
        
        val simpleProcessor = SimpleLogRecordProcessor(simpleExporter)
        val batchProcessor = BatchLogRecordProcessor(batchExporter, maxBatchSize = 3)
        
        val compositeProcessor = CompositeLogRecordProcessor(
            listOf(simpleProcessor, batchProcessor),
            io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler()
        )
        
        // Add two records
        compositeProcessor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        compositeProcessor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        // Simple should have exported both immediately
        assertEquals(2, simpleExporter.logs.size)
        // Batch should not have exported yet (batch size = 3)
        assertEquals(0, batchExporter.logs.size)
        
        // Add third record
        compositeProcessor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        // Simple should have exported all three
        assertEquals(3, simpleExporter.logs.size)
        // Batch should have exported all three
        assertEquals(3, batchExporter.logs.size)
    }

    @Test
    fun testAllThreeProcessorTypes() {
        val simpleExporter = FakeLogRecordExporter()
        val batchExporter = FakeLogRecordExporter()
        
        val noopProcessor = NoopLogRecordProcessor.INSTANCE
        val simpleProcessor = SimpleLogRecordProcessor(simpleExporter)
        val batchProcessor = BatchLogRecordProcessor(batchExporter, maxBatchSize = 2)
        
        val compositeProcessor = CompositeLogRecordProcessor(
            listOf(noopProcessor, simpleProcessor, batchProcessor),
            io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler()
        )
        
        compositeProcessor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        // Only simple should have exported
        assertEquals(1, simpleExporter.logs.size)
        assertEquals(0, batchExporter.logs.size)
        
        compositeProcessor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        // Simple should have both, batch should trigger export now
        assertEquals(2, simpleExporter.logs.size)
        assertEquals(2, batchExporter.logs.size)
        
        assertEquals(OperationResultCode.Success, compositeProcessor.shutdown())
    }
}