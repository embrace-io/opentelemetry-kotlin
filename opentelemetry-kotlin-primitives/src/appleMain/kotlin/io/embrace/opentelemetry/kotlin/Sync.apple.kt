package io.embrace.opentelemetry.kotlin

public actual inline fun <R> sync(lock: Any, block: () -> R): R {
    throw UnsupportedOperationException()
}

public actual inline fun <R> doubleCheckedLockSync(
    lock: Any,
    condition: () -> Boolean,
    block: () -> R
) {
    throw UnsupportedOperationException()
}

