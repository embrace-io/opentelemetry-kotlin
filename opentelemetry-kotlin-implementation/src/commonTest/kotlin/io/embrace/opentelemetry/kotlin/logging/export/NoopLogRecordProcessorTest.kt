package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadWriteLogRecord
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class NoopLogRecordProcessorTest {

    private val fakeLogRecord = FakeReadWriteLogRecord()
    private val fakeContext = FakeContext()

    @Test
    fun testSingletonInstance() {
        val instance1 = NoopLogRecordProcessor.INSTANCE
        val instance2 = NoopLogRecordProcessor.INSTANCE
        assertSame(instance1, instance2)
    }

    @Test
    fun testOnEmitDoesNothing() {
        val processor = NoopLogRecordProcessor.INSTANCE
        // This should not throw any exceptions
        processor.onEmit(fakeLogRecord, fakeContext)
    }

    @Test
    fun testForceFlushReturnsSuccess() {
        val processor = NoopLogRecordProcessor.INSTANCE
        assertEquals(OperationResultCode.Success, processor.forceFlush())
    }

    @Test
    fun testShutdownReturnsSuccess() {
        val processor = NoopLogRecordProcessor.INSTANCE
        assertEquals(OperationResultCode.Success, processor.shutdown())
    }

    @Test
    fun testMultipleOperationsDoNotAffectEachOther() {
        val processor = NoopLogRecordProcessor.INSTANCE
        
        // Call operations multiple times
        processor.onEmit(fakeLogRecord, fakeContext)
        assertEquals(OperationResultCode.Success, processor.forceFlush())
        processor.onEmit(fakeLogRecord, fakeContext)
        assertEquals(OperationResultCode.Success, processor.shutdown())
        processor.onEmit(fakeLogRecord, fakeContext)
        assertEquals(OperationResultCode.Success, processor.forceFlush())
    }
}