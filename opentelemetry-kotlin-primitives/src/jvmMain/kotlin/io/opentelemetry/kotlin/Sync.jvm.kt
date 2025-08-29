package io.opentelemetry.kotlin

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

public actual inline fun <R> sync(lock: Any, block: () -> R): R =
    synchronized(lock) {
        block()
    }

public actual class ReentrantReadWriteLock {
    private val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()

    public actual fun <T> write(action: () -> T): T = lock.write { action() }

    public actual fun <T> read(action: () -> T): T = lock.read { action() }
}
