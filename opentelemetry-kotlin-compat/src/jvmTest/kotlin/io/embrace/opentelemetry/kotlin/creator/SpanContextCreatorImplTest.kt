package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanContextCreatorImplTest {

    private val creator = createCompatObjectCreator()

    @Test
    fun `test invalid`() {
        assertSame(creator.spanContext.invalid, creator.spanContext.invalid)
    }

    @Test
    fun `test valid`() {
        val generator = CompatTracingIdCreator()
        val traceId = generator.generateTraceId()
        val spanId = generator.generateSpanId()
        val traceFlags = creator.traceFlags.default
        val traceState = creator.traceState.default
        val spanContext = creator.spanContext.create(
            traceId,
            spanId,
            traceFlags,
            traceState
        )
        assertSame(traceId, spanContext.traceId)
        assertSame(spanId, spanContext.spanId)
    }
}
