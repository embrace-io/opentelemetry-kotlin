package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
internal object NoopTraceFlags : TraceFlags {
    override val isSampled: Boolean = false
    override val isRandom: Boolean = false
    override val hex: String = "00"
}
