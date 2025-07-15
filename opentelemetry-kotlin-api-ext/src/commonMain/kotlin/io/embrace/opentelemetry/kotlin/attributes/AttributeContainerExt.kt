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
    attributes.forEach {
        when (val input = it.value) {
            is String -> setStringAttribute(it.key, input)
            is Boolean -> setBooleanAttribute(it.key, input)
            is Long -> setLongAttribute(it.key, input)
            is Double -> setDoubleAttribute(it.key, input)
            is Collection<*> -> handleCollection(it.key, input.toList())
            is Array<*> -> handleCollection(it.key, input.toList())
            else -> setStringAttribute(it.key, it.value.toString())
        }
    }
}

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalApi::class)
private fun AttributeContainer.handleCollection(key: String, input: List<*>) {
    when {
        input.all { it is String } -> setStringListAttribute(key, input as List<String>)
        input.all { it is Boolean } -> setBooleanListAttribute(key, input as List<Boolean>)
        input.all { it is Double } -> setDoubleListAttribute(key, input as List<Double>)
        input.all { it is Long } -> setLongListAttribute(key, input as List<Long>)
        else -> return
    }
}
