package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.getCurrentTimeNanos

@OptIn(ExperimentalApi::class)
internal class ClockImpl : Clock {
    override fun now(): Long = getCurrentTimeNanos()
}
