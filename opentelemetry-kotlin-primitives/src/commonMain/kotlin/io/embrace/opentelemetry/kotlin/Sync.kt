package io.embrace.opentelemetry.kotlin

/**
 * Executes the block synchronously with an exclusion lock.
 */
public expect inline fun <R> sync(lock: Any, block: () -> R): R
