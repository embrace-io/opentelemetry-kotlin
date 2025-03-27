package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.StatusCode
import java.util.concurrent.TimeUnit

internal class SpanAdapter(private val span: io.embrace.opentelemetry.kotlin.tracing.Span) : Span {

    override fun <T : Any?> setAttribute(key: AttributeKey<T>, value: T): Span {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, attributes: Attributes): Span {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, attributes: Attributes, timestamp: Long, unit: TimeUnit): Span {
        TODO("Not yet implemented")
    }

    override fun setStatus(statusCode: StatusCode, description: String): Span {
        statusCode.convertToOtelKotlin(description)
        return this
    }

    override fun recordException(exception: Throwable, additionalAttributes: Attributes): Span {
        TODO("Not yet implemented")
    }

    override fun updateName(name: String): Span {
        span.name = name
        return this
    }

    override fun end() {
        span.end()
    }

    override fun end(timestamp: Long, unit: TimeUnit) {
        span.end(unit.toNanos(timestamp))
    }

    override fun getSpanContext(): SpanContext {
        TODO("Not yet implemented")
    }

    override fun isRecording(): Boolean = span.isRecording()
}
