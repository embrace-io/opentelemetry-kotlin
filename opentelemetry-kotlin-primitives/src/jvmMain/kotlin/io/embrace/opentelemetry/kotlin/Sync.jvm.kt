package io.embrace.opentelemetry.kotlin

public actual inline fun <R> sync(lock: Any, block: () -> R): R =
    synchronized(lock) {
        block()
    }

public actual inline fun <R> doubleCheckedLockSync(
    lock: Any,
    condition: () -> Boolean,
    block: () -> R
) {
    if (condition()) {
        synchronized(lock) {
            if (condition()) {
                block()
            }
        }
    }
}
