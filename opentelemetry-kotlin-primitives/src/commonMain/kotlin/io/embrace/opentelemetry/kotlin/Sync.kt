package io.embrace.opentelemetry.kotlin

/**
 * Executes the block synchronously with an exclusion lock.
 */
public expect inline fun <R> sync(lock: Any, block: () -> R): R

/**
 * Performs an operation if the condition is true, using a double-checked lock. If the condition
 * is not true, the block is not executed.
 *
 * https://en.wikipedia.org/wiki/Double-checked_locking
 */
public expect inline fun <R> doubleCheckedLockSync(lock: Any, condition: () -> Boolean, block: () -> R)
