package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.Clock

public class ClockAdapter(
    private val clock: OtelJavaClock = OtelJavaClock.getDefault()
) : Clock {
    override fun now(): Long = clock.now()
}
