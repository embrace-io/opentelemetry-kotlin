package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.NoopTraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal object NoopTraceFlagsCreator : TraceFlagsCreator {
    override val default: TraceFlags = NoopTraceFlags
    override fun create(sampled: Boolean, random: Boolean): TraceFlags = NoopTraceFlags
    override fun fromHex(hex: String): TraceFlags = NoopTraceFlags
}
