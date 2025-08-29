package io.opentelemetry.kotlin.clock

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi

internal expect fun getCurrentTimeNanos(): Long

@OptIn(ExperimentalApi::class)
internal class ClockImpl : Clock {
    override fun now(): Long = getCurrentTimeNanos()
}
