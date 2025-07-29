package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.assertions.assertSpanContextsMatch
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeClock
import io.embrace.opentelemetry.kotlin.k2j.context.root
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.create
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.default
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.invalid
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanExtTest {

    private val generator = OtelJavaIdGenerator.random()

    private val validSpanContext = SpanContext.create(
        traceId = generator.generateTraceId(),
        spanId = generator.generateSpanId(),
        traceState = TraceState.default(),
        traceFlags = TraceFlags.default()
    )

    @Test
    fun `test invalid span`() {
        val invalid = Span.invalid()
        assertSpanContextsMatch(SpanContext.invalid(), invalid.spanContext)
        assertSpanContextsMatch(SpanContext.invalid(), invalid.parent)
    }

    @Test
    fun `test from span context valid`() {
        val span = Span.fromSpanContext(validSpanContext)
        assertSpanContextsMatch(validSpanContext, span.spanContext)
        assertSpanContextsMatch(SpanContext.invalid(), span.parent)
    }

    @Test
    fun `test from span context invalid`() {
        val span = Span.fromSpanContext(SpanContext.invalid())
        assertEquals(Span.invalid(), span)
    }

    @Test
    fun `test from context invalid`() {
        val span = Span.fromContext(Context.root())
        assertSpanContextsMatch(SpanContext.invalid(), span.spanContext)
    }

    @Test
    fun `test from context valid`() {
        val spanContext = OtelJavaSpanContext.create(
            generator.generateTraceId(),
            generator.generateSpanId(),
            OtelJavaTraceFlags.getDefault(),
            OtelJavaTraceState.getDefault()
        )
        val span = SpanAdapter(
            OtelJavaSpan.wrap(spanContext),
            FakeClock(),
            OtelJavaContext.root(),
            SpanKind.INTERNAL,
            0
        )
        val root = Context.root()
        val ctx = span.storeInContext(root)
        val observed = Span.fromContext(root).spanContext
        assertSpanContextsMatch(SpanContext.invalid(), observed)

        val retrievedSpan = Span.fromContext(ctx)
        assertSpanContextsMatch(SpanContextAdapter(spanContext), retrievedSpan.spanContext)
    }
}
