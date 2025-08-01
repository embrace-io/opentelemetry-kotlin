package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData

/**
 * Represents a link to a [SpanContext] and optional attributes further describing the link.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#link
 */
@ExperimentalApi
@ThreadSafe
public interface Link : LinkData, AttributeContainer {
    override val attributes: Map<String, Any>
        get() = attributes()
}
