package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.NoopTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal object NoopTraceFlagsCreator : TraceFlagsCreator {
    override val default: TraceFlags = NoopTraceFlags
}
