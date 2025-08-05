package io.embrace.opentelemetry.kotlin

public actual inline fun <R> sync(lock: Any, block: () -> R): R =
    synchronized(lock) {
        block()
    }
