package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock

internal class FakeOtelJavaClock : OtelJavaClock {
    var nanoseconds = 0L
    override fun now(): Long = nanoseconds
    override fun nanoTime(): Long = nanoseconds
}
