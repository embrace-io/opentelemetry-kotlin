package io.embrace.opentelemetry.kotlin.clock

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.datetime.Clock as KotlinxClock

@OptIn(ExperimentalApi::class)
internal class ClockImpl : Clock {
    override fun now(): Long {
        val now = KotlinxClock.System.now()
        return now.epochSeconds * 1_000_000_000L + now.nanosecondsOfSecond
    }
}
