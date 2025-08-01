package io.embrace.opentelemetry.kotlin.tracing.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter

@OptIn(ExperimentalApi::class)
public fun OtelJavaSpanContext.toOtelKotlinSpanContext(): SpanContext = SpanContextAdapter(this)
