package io.embrace.opentelemetry.kotlin

public actual class ReentrantReadWriteLock {
    public actual fun <T> write(action: () -> T): T = throw UnsupportedOperationException()
    public actual fun <T> read(action: () -> T): T = throw UnsupportedOperationException()
}

