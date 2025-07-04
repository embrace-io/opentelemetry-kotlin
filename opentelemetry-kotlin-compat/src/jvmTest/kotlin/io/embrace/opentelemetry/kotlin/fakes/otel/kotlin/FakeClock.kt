package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.Clock

internal class FakeClock : Clock {
    var nanoseconds = 0L
    override fun now(): Long = nanoseconds
}
