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
internal class SimpleLogRecordProcessorTest {

    private val fakeLogRecord = FakeReadWriteLogRecord()
    private val fakeContext = FakeContext()
    private lateinit var fakeExporter: FakeLogRecordExporter
    private lateinit var processor: SimpleLogRecordProcessor

    @BeforeTest
    fun setUp() {
        fakeExporter = FakeLogRecordExporter()
        processor = SimpleLogRecordProcessor(fakeExporter)
    }

    @Test
    fun testOnEmitExportsImmediately() {
        processor.onEmit(fakeLogRecord, fakeContext)
        
        assertEquals(1, fakeExporter.logs.size)
        assertEquals(fakeLogRecord, fakeExporter.logs[0])
    }

    @Test
    fun testMultipleOnEmitExportsEachSeparately() {
        val logRecord1 = FakeReadWriteLogRecord()
        val logRecord2 = FakeReadWriteLogRecord()
        
        processor.onEmit(logRecord1, fakeContext)
        processor.onEmit(logRecord2, fakeContext)
        
        assertEquals(2, fakeExporter.logs.size)
        assertEquals(logRecord1, fakeExporter.logs[0])
        assertEquals(logRecord2, fakeExporter.logs[1])
    }

    @Test
    fun testForceFlushDelegatestoExporter() {
        fakeExporter.flushCode = { OperationResultCode.Failure }
        assertEquals(OperationResultCode.Failure, processor.forceFlush())
        
        fakeExporter.flushCode = { OperationResultCode.Success }
        assertEquals(OperationResultCode.Success, processor.forceFlush())
    }

    @Test
    fun testShutdownDelegatesToExporter() {
        fakeExporter.shutdownCode = { OperationResultCode.Failure }
        assertEquals(OperationResultCode.Failure, processor.shutdown())
        
        fakeExporter.shutdownCode = { OperationResultCode.Success }
        assertEquals(OperationResultCode.Success, processor.shutdown())
    }

    @Test
    fun testExportFailureDoesNotAffectSubsequentCalls() {
        fakeExporter.action = { OperationResultCode.Failure }
        
        processor.onEmit(fakeLogRecord, fakeContext)
        processor.onEmit(fakeLogRecord, fakeContext)
        
        // Both calls should still result in exports, even if they fail
        assertEquals(2, fakeExporter.logs.size)
    }

    @Test
    fun testOnEmitPassesSingleLogRecordToExporter() {
        var exportedBatches = 0
        fakeExporter.action = { batch ->
            exportedBatches++
            assertEquals(1, batch.size)
            OperationResultCode.Success
        }
        
        processor.onEmit(fakeLogRecord, fakeContext)
        processor.onEmit(fakeLogRecord, fakeContext)
        
        assertEquals(2, exportedBatches)
    }
}