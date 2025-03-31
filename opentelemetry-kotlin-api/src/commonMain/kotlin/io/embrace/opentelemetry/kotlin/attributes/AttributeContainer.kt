package io.embrace.opentelemetry.kotlin.attributes

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Implementations of this interface hold 'attributes' as described in the OTel specification.
 *
 * https://opentelemetry.io/docs/specs/otel/common/#attribute
 */
@ExperimentalApi
public interface AttributeContainer {

    /**
     * Sets an attribute with a boolean value.
     */
    public fun setBooleanAttribute(key: String, value: Boolean)

    /**
     * Sets an attribute with a string value.
     */
    public fun setStringAttribute(key: String, value: String)

    /**
     * Sets an attribute with a long value.
     */
    public fun setLongAttribute(key: String, value: Long)

    /**
     * Sets an attribute with a double value.
     */
    public fun setDoubleAttribute(key: String, value: Double)

    /**
     * Sets an attribute with a list of boolean values.
     */
    public fun setBooleanListAttribute(key: String, value: List<Boolean>)

    /**
     * Sets an attribute with a list of string values.
     */
    public fun setStringListAttribute(key: String, value: List<String>)

    /**
     * Sets an attribute with a list of long values.
     */
    public fun setLongListAttribute(key: String, value: List<Long>)

    /**
     * Sets an attribute with a list of double values.
     */
    public fun setDoubleListAttribute(key: String, value: List<Double>)

    /**
     * Returns a snapshot of the attributes as a map.
     */
    public fun attributes(): Map<String, Any>
}
