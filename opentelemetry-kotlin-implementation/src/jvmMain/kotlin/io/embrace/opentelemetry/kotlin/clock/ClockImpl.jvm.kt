package io.embrace.opentelemetry.kotlin.clock

internal actual fun getCurrentTimeNanos(): Long {
    return System.currentTimeMillis() * 1_000_000L
}
