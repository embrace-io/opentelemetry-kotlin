package io.embrace.opentelemetry.kotlin.clock

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.getCurrentTimeNanos

@OptIn(ExperimentalApi::class)
public class ClockImpl : Clock {
    override fun now(): Long = getCurrentTimeNanos()
}
