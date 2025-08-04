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
import io.embrace.opentelemetry.kotlin.creator.createCompatObjectCreator
import io.embrace.opentelemetry.kotlin.tracing.ext.storeInContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanExtTest {

    private val objectCreator = createCompatObjectCreator()
    private val generator = OtelJavaIdGenerator.random()

    private val validSpanContext = objectCreator.spanContext.create(
        traceId = generator.generateTraceId(),
        spanId = generator.generateSpanId(),
        traceState = objectCreator.traceState.default,
        traceFlags = objectCreator.traceFlags.default,
    )

    @Test
    fun `test invalid span`() {
        val invalid = objectCreator.span.invalid
        assertSpanContextsMatch(objectCreator.spanContext.invalid, invalid.spanContext)
        assertSpanContextsMatch(objectCreator.spanContext.invalid, invalid.parent)
    }

    @Test
    fun `test from span context valid`() {
        val span = objectCreator.span.fromSpanContext(validSpanContext)
        assertSpanContextsMatch(validSpanContext, span.spanContext)
        assertSpanContextsMatch(objectCreator.spanContext.invalid, span.parent)
    }

    @Test
    fun `test from span context invalid`() {
        val span = objectCreator.span.fromSpanContext(objectCreator.spanContext.invalid)
        assertEquals(objectCreator.span.invalid, span)
    }

    @Test
    fun `test from context invalid`() {
        val span = objectCreator.span.fromContext(objectCreator.context.root())
        assertSpanContextsMatch(objectCreator.spanContext.invalid, span.spanContext)
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
        val root = objectCreator.context.root()
        val ctx = span.storeInContext(root)
        val observed = objectCreator.span.fromContext(root).spanContext
        assertSpanContextsMatch(objectCreator.spanContext.invalid, observed)

        val retrievedSpan = objectCreator.span.fromContext(ctx)
        assertSpanContextsMatch(SpanContextAdapter(spanContext), retrievedSpan.spanContext)
    }
}
