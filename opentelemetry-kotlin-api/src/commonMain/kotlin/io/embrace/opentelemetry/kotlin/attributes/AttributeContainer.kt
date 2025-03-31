package io.embrace.opentelemetry.kotlin.attributes

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Implementations of this interface hold 'attributes' as described in the OTel specification.
 *
 * https://opentelemetry.io/docs/specs/otel/common/#attribute
 */
@ExperimentalApi
@ThreadSafe
public interface AttributeContainer {

    /**
     * Sets an attribute with a boolean value.
     */
    @ThreadSafe
    public fun setBooleanAttribute(key: String, value: Boolean)

    /**
     * Sets an attribute with a string value.
     */
    @ThreadSafe
    public fun setStringAttribute(key: String, value: String)

    /**
     * Sets an attribute with a long value.
     */
    @ThreadSafe
    public fun setLongAttribute(key: String, value: Long)

    /**
     * Sets an attribute with a double value.
     */
    @ThreadSafe
    public fun setDoubleAttribute(key: String, value: Double)

    /**
     * Sets an attribute with a list of boolean values.
     */
    @ThreadSafe
    public fun setBooleanListAttribute(key: String, value: List<Boolean>)

    /**
     * Sets an attribute with a list of string values.
     */
    @ThreadSafe
    public fun setStringListAttribute(key: String, value: List<String>)

    /**
     * Sets an attribute with a list of long values.
     */
    @ThreadSafe
    public fun setLongListAttribute(key: String, value: List<Long>)

    /**
     * Sets an attribute with a list of double values.
     */
    @ThreadSafe
    public fun setDoubleListAttribute(key: String, value: List<Double>)

    /**
     * Returns a snapshot of the attributes as a map.
     */
    @ThreadSafe
    public fun attributes(): Map<String, Any>
}
