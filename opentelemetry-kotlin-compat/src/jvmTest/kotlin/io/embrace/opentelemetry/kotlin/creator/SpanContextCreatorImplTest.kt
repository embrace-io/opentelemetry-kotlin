package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.id.TracingIdGeneratorImpl
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.default
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanContextCreatorImplTest {

    private val creator = createCompatObjectCreator().spanContext

    @Test
    fun `test invalid`() {
        assertSame(creator.invalid, creator.invalid)
    }

    @Test
    fun `test valid`() {
        val generator = TracingIdGeneratorImpl()
        val traceId = generator.generateTraceId()
        val spanId = generator.generateSpanId()
        val traceFlags = TraceFlags.default()
        val traceState = TraceState.default()
        val spanContext = creator.create(
            traceId,
            spanId,
            traceFlags,
            traceState
        )
        assertSame(traceId, spanContext.traceId)
        assertSame(spanId, spanContext.spanId)
    }
}
