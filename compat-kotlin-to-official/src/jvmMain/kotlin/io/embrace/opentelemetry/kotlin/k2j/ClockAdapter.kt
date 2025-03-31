package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.Clock

public class ClockAdapter(
    private val clock: io.opentelemetry.sdk.common.Clock = io.opentelemetry.sdk.common.Clock.getDefault()
) : Clock {
    override fun now(): Long = clock.now()
}
