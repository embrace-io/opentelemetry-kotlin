package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.assertions.assertSpanContextsMatch
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.create
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.default
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.invalid
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanExtTest {

    @Test
    fun `test invalid span`() {
        val invalid = Span.invalid()
        assertSpanContextsMatch(SpanContext.invalid(), invalid.spanContext)
        assertSpanContextsMatch(SpanContext.invalid(), invalid.parent)
    }

    @Test
    fun `test from span context valid`() {
        val generator = OtelJavaIdGenerator.random()
        val spanContext = SpanContext.create(
            traceId = generator.generateTraceId(),
            spanId = generator.generateSpanId(),
            traceState = TraceState.default(),
            traceFlags = TraceFlags.default()
        )

        val span = Span.fromSpanContext(spanContext)
        assertSpanContextsMatch(spanContext, span.spanContext)
        assertSpanContextsMatch(SpanContext.invalid(), span.parent)
    }

    @Test
    fun `test from span context invalid`() {
        val span = Span.fromSpanContext(SpanContext.invalid())
        assertEquals(Span.invalid(), span)
    }
}
