package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.assertions.assertSpanContextsMatch
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.factory.createCompatSdkFactory
import io.embrace.opentelemetry.kotlin.tracing.ext.storeInContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanExtTest {

    private val factory = createCompatSdkFactory()
    private val generator = OtelJavaIdGenerator.random()

    private val validSpanContext = factory.spanContext.create(
        traceId = generator.generateTraceId(),
        spanId = generator.generateSpanId(),
        traceState = factory.traceState.default,
        traceFlags = factory.traceFlags.default,
    )

    @Test
    fun `test invalid span`() {
        val invalid = factory.span.invalid
        assertSpanContextsMatch(factory.spanContext.invalid, invalid.spanContext)
        assertSpanContextsMatch(factory.spanContext.invalid, invalid.parent)
    }

    @Test
    fun `test from span context valid`() {
        val span = factory.span.fromSpanContext(validSpanContext)
        assertSpanContextsMatch(validSpanContext, span.spanContext)
        assertSpanContextsMatch(factory.spanContext.invalid, span.parent)
    }

    @Test
    fun `test from span context invalid`() {
        val span = factory.span.fromSpanContext(factory.spanContext.invalid)
        assertEquals(factory.span.invalid, span)
    }

    @Test
    fun `test from context invalid`() {
        val span = factory.span.fromContext(factory.context.root())
        assertSpanContextsMatch(factory.spanContext.invalid, span.spanContext)
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
        val root = factory.context.root()
        val ctx = span.storeInContext(root)
        val observed = factory.span.fromContext(root).spanContext
        assertSpanContextsMatch(factory.spanContext.invalid, observed)

        val retrievedSpan = factory.span.fromContext(ctx)
        assertSpanContextsMatch(SpanContextAdapter(spanContext), retrievedSpan.spanContext)
    }
}
