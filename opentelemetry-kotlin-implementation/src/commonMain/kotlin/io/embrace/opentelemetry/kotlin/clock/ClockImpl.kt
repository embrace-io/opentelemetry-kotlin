package io.embrace.opentelemetry.kotlin.clock

import io.embrace.opentelemetry.kotlin.Clock

internal class ClockImpl : Clock {
    override fun now(): Long = 0
}
