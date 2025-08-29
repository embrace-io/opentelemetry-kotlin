package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.FakeTraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal class FakeTraceFlagsCreator : TraceFlagsCreator {
    override val default: TraceFlags = FakeTraceFlags()
    override fun create(
        sampled: Boolean,
        random: Boolean
    ): TraceFlags = FakeTraceFlags()

    override fun fromHex(hex: String): TraceFlags = FakeTraceFlags()
}
