package io.embrace.opentelemetry.kotlin.clock

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeClock(
    var time: Long = 0
) : Clock {
    override fun now(): Long = time
}
