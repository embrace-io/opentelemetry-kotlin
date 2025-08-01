package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanExporter
import io.embrace.opentelemetry.kotlin.tracing.export.SpanExporter

@OptIn(ExperimentalApi::class)
public fun OtelJavaSpanExporter.toOtelKotlinSpanExporter(): SpanExporter = OtelJavaSpanExporterAdapter(this)
