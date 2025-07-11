package io.embrace.opentelemetry.kotlin.attributes

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Sets attributes on an [AttributeContainer] from a [Map]. Only values in the map of a type supported by the
 * OpenTelemetry API will be set. Other values will be ignored.
 *
 * https://opentelemetry.io/docs/specs/otel/common/#attribute
 */
@ExperimentalApi
@ThreadSafe
public fun AttributeContainer.setAttributes(attributes: Map<String, Any>) {
    // TODO: add support for other attribute types
    attributes.forEach {
        setStringAttribute(it.key, it.value.toString())
    }
}
