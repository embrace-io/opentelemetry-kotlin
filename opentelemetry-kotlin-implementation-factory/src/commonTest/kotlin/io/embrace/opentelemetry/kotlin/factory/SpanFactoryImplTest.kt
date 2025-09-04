package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalApi::class)
internal class SpanFactoryImplTest {

    private val sdkFactory = createSdkFactory()
    private val factory = sdkFactory.spanFactory

    @Test
    fun testInvalidSpan() {
        assertFalse(factory.invalid.spanContext.isValid)
    }

    @Test
    fun testFromSpanContext() {
        val spanContext = FakeSpanContext(
            traceId = "12345678901234567890123456789012",
            spanId = "1234567890123456",
        )
        assertEquals(spanContext, factory.fromSpanContext(spanContext).spanContext)
    }
}
