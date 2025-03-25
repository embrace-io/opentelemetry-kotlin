package io.embrace.opentelemetry.kotlin.k2j.framework

import io.opentelemetry.sdk.common.Clock

internal class FakeClock : Clock {
    var nanoseconds = 0L
    override fun now(): Long = nanoseconds
    override fun nanoTime(): Long = nanoseconds
}
