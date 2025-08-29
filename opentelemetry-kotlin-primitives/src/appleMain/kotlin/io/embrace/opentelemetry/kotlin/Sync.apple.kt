package io.embrace.opentelemetry.kotlin

public actual inline fun <R> sync(lock: Any, block: () -> R): R {
    throw UnsupportedOperationException()
}

public actual class ReentrantReadWriteLock {
    public actual fun <T> write(action: () -> T): T = throw UnsupportedOperationException()
    public actual fun <T> read(action: () -> T): T = throw UnsupportedOperationException()
}
