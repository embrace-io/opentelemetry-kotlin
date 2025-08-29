package io.embrace.opentelemetry.kotlin

public actual inline fun <R> sync(lock: Any, block: () -> R): R = block()

public actual class ReentrantReadWriteLock {
    public actual fun <T> write(action: () -> T): T = action()
    public actual fun <T> read(action: () -> T): T = action()
}
