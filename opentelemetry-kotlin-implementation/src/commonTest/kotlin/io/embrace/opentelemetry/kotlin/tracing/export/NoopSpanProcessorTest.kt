package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.FakeReadWriteSpan
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalApi::class)
internal class NoopSpanProcessorTest {

    private val fakeSpan = FakeReadWriteSpan()
    private val fakeContext = FakeContext()

    @Test
    fun testOnStartDoesNothing() {
        NoopSpanProcessor.onStart(fakeSpan, fakeContext)
        // Should complete without throwing
    }

    @Test
    fun testOnEndDoesNothing() {
        NoopSpanProcessor.onEnd(fakeSpan)
        // Should complete without throwing
    }

    @Test
    fun testIsStartRequiredReturnsFalse() {
        assertFalse(NoopSpanProcessor.isStartRequired())
    }

    @Test
    fun testIsEndRequiredReturnsFalse() {
        assertFalse(NoopSpanProcessor.isEndRequired())
    }

    @Test
    fun testForceFlushReturnsSuccess() {
        assertEquals(OperationResultCode.Success, NoopSpanProcessor.forceFlush())
    }

    @Test
    fun testShutdownReturnsSuccess() {
        assertEquals(OperationResultCode.Success, NoopSpanProcessor.shutdown())
    }
}