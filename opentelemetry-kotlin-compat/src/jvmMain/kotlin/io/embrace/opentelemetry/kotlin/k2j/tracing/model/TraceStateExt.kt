package io.embrace.opentelemetry.kotlin.k2j.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.k2j.tracing.TraceStateAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
public fun TraceState.Companion.default(): TraceState =
    TraceStateAdapter(OtelJavaTraceState.getDefault())
