package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal class FakeTraceFlags(
    override val isSampled: Boolean = false,
    override val isRandom: Boolean = false,
    override val hex: String = "00"
) : TraceFlags
