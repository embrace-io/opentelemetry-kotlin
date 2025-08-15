package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.OperationResultCode.Failure
import io.embrace.opentelemetry.kotlin.export.OperationResultCode.Success
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadWriteLogRecord
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class CompositeLogRecordProcessorTest {

    private val fakeLogRecord = FakeReadWriteLogRecord()
    private val fakeContext = FakeContext()
    private lateinit var errorHandler: FakeSdkErrorHandler

    @BeforeTest
    fun setUp() {
        errorHandler = FakeSdkErrorHandler()
    }

    @Test
    fun `test no span processors`() {
        val processor = CompositeLogRecordProcessor(emptyList(), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun `test multiple span processors`() {
        val first = FakeLogRecordProcessor()
        val second = FakeLogRecordProcessor()
        val processor = CompositeLogRecordProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertTelemetryCapturedSuccess(first, second)
    }

    @Test
    fun `test one processor flush fails`() {
        val first = FakeLogRecordProcessor(flushCode = { Failure })
        val second = FakeLogRecordProcessor()
        val processor = CompositeLogRecordProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Failure,
            Success,
        )
        assertTelemetryCapturedSuccess(first, second)
    }

    @Test
    fun `test one processor shutdown fails`() {
        val first = FakeLogRecordProcessor(shutdownCode = { Failure })
        val second = FakeLogRecordProcessor()
        val processor = CompositeLogRecordProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Failure,
        )
        assertTelemetryCapturedSuccess(first, second)
    }

    @Test
    fun `test one processor throws exception in onEmit`() {
        val first = FakeLogRecordProcessor(action = { _, _ -> throw IllegalStateException() })
        val second = FakeLogRecordProcessor()
        val processor = CompositeLogRecordProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    @Test
    fun `test one processor throws exception in flush`() {
        val first = FakeLogRecordProcessor(flushCode = { throw IllegalStateException() })
        val second = FakeLogRecordProcessor()
        val processor = CompositeLogRecordProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Failure,
            Success,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    @Test
    fun `test one processor throws exception in shutdown`() {
        val first = FakeLogRecordProcessor(shutdownCode = { throw IllegalStateException() })
        val second = FakeLogRecordProcessor()
        val processor = CompositeLogRecordProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Failure,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    private fun CompositeLogRecordProcessor.assertReturnValuesMatch(
        flush: OperationResultCode,
        shutdown: OperationResultCode
    ) {
        assertEquals(shutdown, shutdown())
        assertEquals(flush, forceFlush())
        onEmit(fakeLogRecord, fakeContext)
    }

    private fun assertTelemetryCapturedSuccess(
        first: FakeLogRecordProcessor,
        second: FakeLogRecordProcessor
    ) {
        assertFalse(errorHandler.hasErrors())
        assertSame(fakeLogRecord, first.logs.single())
        assertSame(fakeLogRecord, second.logs.single())
    }

    private fun assertTelemetryCapturedFailure(
        first: FakeLogRecordProcessor,
        second: FakeLogRecordProcessor
    ) {
        assertTrue(errorHandler.hasErrors())
        assertEquals(1, errorHandler.userCodeErrors.size)
        assertSame(fakeLogRecord, first.logs.single())
        assertSame(fakeLogRecord, second.logs.single())
    }
}
