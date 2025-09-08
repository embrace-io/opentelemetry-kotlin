package io.embrace.opentelemetry.kotlin

/**
 * Executes the block synchronously using the supplied lock to prevent other threads from
 * concurrently executing on the same lock.
 */
public expect inline fun <R> sync(lock: Any, block: () -> R): R

/**
 * A reentrant implementation of a read-write lock.
 */
public expect class ReentrantReadWriteLock() {

    /**
     * Performs an operation behind the write lock.
     */
    public fun <T> write(action: () -> T): T

    /**
     * Performs an operation behind the read lock.
     */
    public fun <T> read(action: () -> T): T
}
