package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock

internal class FakeClock : OtelJavaClock {
    var nanoseconds = 0L
    override fun now(): Long = nanoseconds
    override fun nanoTime(): Long = nanoseconds
}
