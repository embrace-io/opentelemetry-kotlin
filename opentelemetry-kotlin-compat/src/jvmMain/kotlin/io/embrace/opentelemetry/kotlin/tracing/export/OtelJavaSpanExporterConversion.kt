package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanExporter

@OptIn(ExperimentalApi::class)
public fun OtelJavaSpanExporter.toOtelKotlinSpanExporter(): SpanExporter = OtelJavaSpanExporterAdapter(this)
