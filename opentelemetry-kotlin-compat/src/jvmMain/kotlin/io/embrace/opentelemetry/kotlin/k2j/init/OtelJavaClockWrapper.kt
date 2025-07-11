package io.embrace.opentelemetry.kotlin.k2j.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock

@OptIn(ExperimentalApi::class)
internal class OtelJavaClockWrapper(
    private val impl: Clock
) : OtelJavaClock {

    override fun now(): Long = impl.now()

    override fun nanoTime(): Long = impl.now()
}
