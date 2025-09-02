package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadWriteLogRecord
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class BatchLogRecordProcessorTest {

    private val fakeContext = FakeContext()
    private lateinit var fakeExporter: FakeLogRecordExporter

    @BeforeTest
    fun setUp() {
        fakeExporter = FakeLogRecordExporter()
    }

    @Test
    fun testBatchingWhenMaxBatchSizeReached() {
        val processor = BatchLogRecordProcessor(fakeExporter, maxBatchSize = 3)
        
        // Add records one by one
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(0, fakeExporter.logs.size) // No export yet
        
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(3, fakeExporter.logs.size) // Batch exported
    }

    @Test
    fun testForceFlushExportsPartialBatch() {
        val processor = BatchLogRecordProcessor(fakeExporter, maxBatchSize = 5)
        
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(0, fakeExporter.logs.size) // No export yet
        
        assertEquals(OperationResultCode.Success, processor.forceFlush())
        assertEquals(2, fakeExporter.logs.size) // Partial batch exported
    }

    @Test
    fun testForceFlushWithEmptyBatch() {
        val processor = BatchLogRecordProcessor(fakeExporter)
        assertEquals(OperationResultCode.Success, processor.forceFlush())
        assertEquals(0, fakeExporter.logs.size)
    }

    @Test
    fun testShutdownExportsRemainingLogs() {
        val processor = BatchLogRecordProcessor(fakeExporter, maxBatchSize = 5)
        
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(0, fakeExporter.logs.size) // No export yet
        
        assertEquals(OperationResultCode.Success, processor.shutdown())
        assertEquals(2, fakeExporter.logs.size) // Remaining logs exported
    }

    @Test
    fun testOnEmitAfterShutdownIsIgnored() {
        val processor = BatchLogRecordProcessor(fakeExporter)
        
        processor.shutdown()
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        assertEquals(0, fakeExporter.logs.size)
    }

    @Test
    fun testMaxQueueSizeDropsRecords() {
        val processor = BatchLogRecordProcessor(
            fakeExporter,
            maxBatchSize = 10,
            maxQueueSize = 3
        )
        
        // Fill up the queue
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        // This record should be dropped
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        processor.forceFlush()
        assertEquals(3, fakeExporter.logs.size) // Only first 3 records
    }

    @Test
    fun testExportFailureInForceFlush() {
        fakeExporter.action = { OperationResultCode.Failure }
        val processor = BatchLogRecordProcessor(fakeExporter)
        
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(OperationResultCode.Failure, processor.forceFlush())
    }

    @Test
    fun testShutdownWithExportFailure() {
        fakeExporter.action = { OperationResultCode.Failure }
        val processor = BatchLogRecordProcessor(fakeExporter)
        
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(OperationResultCode.Failure, processor.shutdown())
    }

    @Test
    fun testShutdownWithExporterShutdownFailure() {
        fakeExporter.shutdownCode = { OperationResultCode.Failure }
        val processor = BatchLogRecordProcessor(fakeExporter)
        
        assertEquals(OperationResultCode.Failure, processor.shutdown())
    }

    @Test
    fun testForceFlushCallsExporterForceFlush() {
        var exporterForceFlushCalled = false
        fakeExporter.flushCode = {
            exporterForceFlushCalled = true
            OperationResultCode.Success
        }
        
        val processor = BatchLogRecordProcessor(fakeExporter)
        processor.forceFlush()
        
        assertTrue(exporterForceFlushCalled)
    }

    @Test
    fun testDefaultBatchSizeConstants() {
        assertEquals(512, BatchLogRecordProcessor.DEFAULT_MAX_BATCH_SIZE)
        assertEquals(2048, BatchLogRecordProcessor.DEFAULT_MAX_QUEUE_SIZE)
    }

    @Test
    fun testMultipleBatchExports() {
        val processor = BatchLogRecordProcessor(fakeExporter, maxBatchSize = 2)
        
        // First batch
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(2, fakeExporter.logs.size)
        
        // Second batch
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        assertEquals(4, fakeExporter.logs.size)
    }

    @Test
    fun testConcurrentOnEmitWithForceFlush() {
        val processor = BatchLogRecordProcessor(fakeExporter, maxBatchSize = 10)
        
        // Simulate concurrent onEmit and forceFlush
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        processor.forceFlush()
        processor.onEmit(FakeReadWriteLogRecord(), fakeContext)
        
        assertEquals(3, fakeExporter.logs.size)
    }
}