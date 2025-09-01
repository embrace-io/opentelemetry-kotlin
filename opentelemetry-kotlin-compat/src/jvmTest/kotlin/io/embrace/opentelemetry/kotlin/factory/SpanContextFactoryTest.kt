package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanContextFactoryTest {

    private val factory = createCompatSdkFactory()

    @Test
    fun `test invalid`() {
        assertSame(factory.spanContext.invalid, factory.spanContext.invalid)
    }

    @Test
    fun `test valid`() {
        val generator = CompatTracingIdFactory()
        val traceId = generator.generateTraceId()
        val spanId = generator.generateSpanId()
        val traceFlags = factory.traceFlags.default
        val traceState = factory.traceState.default
        val spanContext = factory.spanContext.create(
            traceId,
            spanId,
            traceFlags,
            traceState
        )
        assertSame(traceId, spanContext.traceId)
        assertSame(spanId, spanContext.spanId)
    }
}
