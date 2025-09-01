package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.NoopTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal object NoopTraceFlagsFactory : TraceFlagsFactory {
    override val default: TraceFlags = NoopTraceFlags
    override fun create(sampled: Boolean, random: Boolean): TraceFlags = NoopTraceFlags
    override fun fromHex(hex: String): TraceFlags = NoopTraceFlags
}
