package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SpanCreatorImplTest {

    private val creator = createCompatObjectCreator()

    @Test
    fun `test invalid`() {
        assertSame(creator.spanContext.invalid, creator.spanContext.invalid)
    }

    @Test
    fun `test from context`() {
        val ctx = creator.context.root()
        val span = creator.span.fromContext(ctx)
        assertFalse(span.spanContext.isValid)
    }

    @Test
    fun `test from span context`() {
        val generator = CompatTracingIdCreator()
        val spanContext = creator.spanContext.create(
            traceId = generator.generateTraceId(),
            spanId = generator.generateSpanId(),
            traceState = creator.traceState.default,
            traceFlags = creator.traceFlags.default,
        )
        val span = creator.span.fromSpanContext(spanContext)
        assertTrue(span.spanContext.isValid)
    }
}
