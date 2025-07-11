package io.embrace.opentelemetry.kotlin.clock

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ClockImpl : Clock {
    override fun now(): Long = 0
}
