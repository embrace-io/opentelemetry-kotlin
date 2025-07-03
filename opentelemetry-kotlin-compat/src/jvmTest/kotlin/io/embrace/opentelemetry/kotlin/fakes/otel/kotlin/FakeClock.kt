package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.Clock

internal class FakeClock : Clock {
    override fun now(): Long = 0
}
