package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal class FakeTraceFlagsCreator : TraceFlagsCreator {
    override val default: TraceFlags = FakeTraceFlags()
    override fun create(
        sampled: Boolean,
        random: Boolean
    ): TraceFlags = FakeTraceFlags()

    override fun fromHex(hex: String): TraceFlags = FakeTraceFlags()
}
