package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.model.Span

/**
 * Record an exception on the span as an event.
 */
@ExperimentalApi
@ThreadSafe
public fun Span.recordException(exception: Throwable, action: AttributeContainer.() -> Unit = {}) {
    addEvent("exception") {
        setStringAttribute("exception.stacktrace", exception.stackTraceToString())
        exception.message?.let {
            setStringAttribute("exception.message", it)
        }
        exception::class.qualifiedName?.let {
            setStringAttribute("exception.type", it)
        }
        action(this)
    }
}
