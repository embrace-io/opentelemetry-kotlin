package io.opentelemetry.kotlin.tracing.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaSpanExporter

@OptIn(ExperimentalApi::class)
public fun OtelJavaSpanExporter.toOtelKotlinSpanExporter(): SpanExporter = OtelJavaSpanExporterAdapter(this)
