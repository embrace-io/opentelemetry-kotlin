package io.embrace.opentelemetry.kotlin

/**
 * A clock that provides the current time in nanoseconds.
 */
public interface Clock {

    /**
     * Returns the current time in nanoseconds.
     */
    public fun now(): Long
}
