package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlagsAdapter

@OptIn(ExperimentalApi::class)
internal class TraceFlagsCreatorImpl : TraceFlagsCreator {
    override val default: TraceFlags by lazy { TraceFlagsAdapter(OtelJavaTraceFlags.getDefault()) }
}
