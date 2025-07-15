package io.embrace.opentelemetry.kotlin.k2j.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.k2j.tracing.TraceFlagsAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
public fun TraceFlags.Companion.default(): TraceFlagsAdapter =
    TraceFlagsAdapter(OtelJavaTraceFlags.getDefault())
