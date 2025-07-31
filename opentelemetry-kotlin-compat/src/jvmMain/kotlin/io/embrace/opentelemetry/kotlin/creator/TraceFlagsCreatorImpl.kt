package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.k2j.tracing.TraceFlagsAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal class TraceFlagsCreatorImpl : TraceFlagsCreator {
    override val default: TraceFlags by lazy { TraceFlagsAdapter(OtelJavaTraceFlags.getDefault()) }
}
