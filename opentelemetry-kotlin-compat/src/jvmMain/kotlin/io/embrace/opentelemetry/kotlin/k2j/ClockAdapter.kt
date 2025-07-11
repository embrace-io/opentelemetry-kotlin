package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock

@OptIn(ExperimentalApi::class)
public class ClockAdapter(
    private val clock: OtelJavaClock = OtelJavaClock.getDefault()
) : Clock {
    override fun now(): Long = clock.now()
}
