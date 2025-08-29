package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.ThreadSafe
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.tracing.model.Span

/**
 * Record an exception on the span as an event.
 */
@ExperimentalApi
@ThreadSafe
public fun Span.recordException(exception: Throwable, attributes: MutableAttributeContainer.() -> Unit = {}) {
    addEvent("exception") {
        setStringAttribute("exception.stacktrace", exception.stackTraceToString())
        exception.message?.let {
            setStringAttribute("exception.message", it)
        }
        exception::class.qualifiedName?.let {
            setStringAttribute("exception.type", it)
        }
        attributes(this)
    }
}
