package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.Span
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class SpanAdapter(private val span: Span) : OtelJavaSpan {

    override fun <T : Any?> setAttribute(key: OtelJavaAttributeKey<T?>, value: T?): OtelJavaSpan? {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, attributes: OtelJavaAttributes): OtelJavaSpan {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, attributes: OtelJavaAttributes, timestamp: Long, unit: TimeUnit): OtelJavaSpan {
        TODO("Not yet implemented")
    }

    override fun setStatus(statusCode: OtelJavaStatusCode, description: String): OtelJavaSpan {
        statusCode.convertToOtelKotlin(description)
        return this
    }

    override fun recordException(exception: Throwable, additionalAttributes: OtelJavaAttributes): OtelJavaSpan {
        TODO("Not yet implemented")
    }

    override fun updateName(name: String): OtelJavaSpan {
        span.name = name
        return this
    }

    override fun end() {
        span.end()
    }

    override fun end(timestamp: Long, unit: TimeUnit) {
        span.end(unit.toNanos(timestamp))
    }

    override fun getSpanContext(): OtelJavaSpanContext {
        TODO("Not yet implemented")
    }

    override fun isRecording(): Boolean = span.isRecording()
}
