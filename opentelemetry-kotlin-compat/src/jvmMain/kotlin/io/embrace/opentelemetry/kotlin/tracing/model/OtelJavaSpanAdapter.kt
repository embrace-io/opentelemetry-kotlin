package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.tracing.conversion.toOtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.conversion.toOtelKotlinStatusData
import io.embrace.opentelemetry.kotlin.tracing.recordException
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.context.Context
import io.opentelemetry.context.ImplicitContextKeyed
import io.opentelemetry.context.Scope
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanAdapter(private val span: Span) : OtelJavaSpan, ImplicitContextKeyed {

    override fun <T : Any?> setAttribute(key: OtelJavaAttributeKey<T?>, value: T?): OtelJavaSpan {
        span.setStringAttribute(key.key, value.toString())
        return this
    }

    override fun addEvent(name: String, attributes: OtelJavaAttributes): OtelJavaSpan {
        span.addEvent(name) {
            attributes.toMap().forEach {
                setStringAttribute(it.key, it.value.toString())
            }
        }
        return this
    }

    override fun addEvent(
        name: String,
        attributes: OtelJavaAttributes,
        timestamp: Long,
        unit: TimeUnit
    ): OtelJavaSpan {
        val time = unit.toNanos(timestamp)
        span.addEvent(name, time) {
            attributes.toMap().forEach {
                setStringAttribute(it.key, it.value.toString())
            }
        }
        return this
    }

    override fun addLink(
        spanContext: SpanContext,
        attributes: Attributes
    ): OtelJavaSpan {
        span.addLink(SpanContextAdapter(spanContext)) {
            attributes.toMap().forEach {
                setStringAttribute(it.key, it.value.toString())
            }
        }
        return this
    }

    override fun setStatus(statusCode: OtelJavaStatusCode, description: String): OtelJavaSpan {
        span.status = statusCode.toOtelKotlinStatusData(description)
        return this
    }

    override fun recordException(
        exception: Throwable,
        additionalAttributes: OtelJavaAttributes
    ): OtelJavaSpan {
        span.recordException(exception) {
            additionalAttributes.toMap().forEach {
                setStringAttribute(it.key, it.value.toString())
            }
        }
        return this
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
        return span.spanContext.toOtelJavaSpanContext()
    }

    override fun isRecording(): Boolean = span.isRecording()

    override fun storeInContext(context: Context): Context {
        return if ((span is ImplicitContextKeyed)) {
            span.storeInContext(context)
        } else {
            super.storeInContext(context)
        }
    }

    override fun makeCurrent(): Scope {
        return if ((span is ImplicitContextKeyed)) {
            span.makeCurrent()
        } else {
            super<ImplicitContextKeyed>.makeCurrent()
        }
    }
}
