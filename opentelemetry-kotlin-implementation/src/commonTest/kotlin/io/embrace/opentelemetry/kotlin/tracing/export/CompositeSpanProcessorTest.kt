package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.OperationResultCode.Failure
import io.embrace.opentelemetry.kotlin.export.OperationResultCode.Success
import io.embrace.opentelemetry.kotlin.tracing.FakeReadWriteSpan
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class CompositeSpanProcessorTest {

    private val fakeSpan = FakeReadWriteSpan()
    private val fakeContext = FakeContext()
    private lateinit var errorHandler: FakeSdkErrorHandler

    @BeforeTest
    fun setUp() {
        errorHandler = FakeSdkErrorHandler()
    }

    @Test
    fun `test no span processors`() {
        val processor = CompositeSpanProcessor(emptyList(), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertFalse(errorHandler.hasErrors())
        assertTrue(processor.isStartRequired())
        assertTrue(processor.isEndRequired())
    }

    @Test
    fun `test processor not invoked`() {
        val impl = FakeSpanProcessor(startRequired = false, endRequired = false)
        val other = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(impl, other), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertTrue(impl.startCalls.isEmpty())
        assertTrue(impl.endCalls.isEmpty())
        assertEquals(1, other.startCalls.size)
        assertEquals(1, other.endCalls.size)
    }

    @Test
    fun `test multiple span processors`() {
        val first = FakeSpanProcessor()
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertTelemetryCapturedSuccess(first, second)
    }

    @Test
    fun `test one processor flush fails`() {
        val first = FakeSpanProcessor(flushCode = { Failure })
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Failure,
            Success,
        )
        assertTelemetryCapturedSuccess(first, second)
    }

    @Test
    fun `test one processor shutdown fails`() {
        val first = FakeSpanProcessor(shutdownCode = { Failure })
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Failure,
        )
        assertTelemetryCapturedSuccess(first, second)
    }

    @Test
    fun `test one processor throws exception in onStart`() {
        val first = FakeSpanProcessor(startAction = { _, _ -> throw IllegalStateException() })
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    @Test
    fun `test one processor throws exception in onEnd`() {
        val first = FakeSpanProcessor(endAction = { throw IllegalStateException() })
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Success,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    @Test
    fun `test one processor throws exception in flush`() {
        val first = FakeSpanProcessor(flushCode = { throw IllegalStateException() })
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Failure,
            Success,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    @Test
    fun `test one processor throws exception in shutdown`() {
        val first = FakeSpanProcessor(shutdownCode = { throw IllegalStateException() })
        val second = FakeSpanProcessor()
        val processor = CompositeSpanProcessor(listOf(first, second), errorHandler)
        processor.assertReturnValuesMatch(
            Success,
            Failure,
        )
        assertTelemetryCapturedFailure(first, second)
    }

    private fun CompositeSpanProcessor.assertReturnValuesMatch(
        flush: OperationResultCode,
        shutdown: OperationResultCode
    ) {
        assertEquals(shutdown, shutdown())
        assertEquals(flush, forceFlush())
        onStart(fakeSpan, fakeContext)
        onEnd(fakeSpan)
    }

    private fun assertTelemetryCapturedSuccess(
        first: FakeSpanProcessor,
        second: FakeSpanProcessor
    ) {
        assertFalse(errorHandler.hasErrors())
        assertSame(fakeSpan, first.startCalls.single())
        assertSame(fakeSpan, first.endCalls.single())
        assertSame(fakeSpan, second.startCalls.single())
        assertSame(fakeSpan, second.endCalls.single())
    }

    private fun assertTelemetryCapturedFailure(
        first: FakeSpanProcessor,
        second: FakeSpanProcessor
    ) {
        assertTrue(errorHandler.hasErrors())
        assertEquals(1, errorHandler.userCodeErrors.size)
        assertSame(fakeSpan, first.startCalls.single())
        assertSame(fakeSpan, first.endCalls.single())
        assertSame(fakeSpan, second.startCalls.single())
        assertSame(fakeSpan, second.endCalls.single())
    }
}
