package io.embrace.opentelemetry.kotlin

import platform.Foundation.NSRecursiveLock

public actual class ReentrantReadWriteLock {

    private val lock = NSRecursiveLock()

    public actual fun <T> write(action: () -> T): T {
        lock.lock()
        try {
            return action()
        } finally {
            lock.unlock()
        }
    }

    // take perf hit of obtaining the same lock for now, for the sake of simplicity
    public actual fun <T> read(action: () -> T): T = write(action)
}
