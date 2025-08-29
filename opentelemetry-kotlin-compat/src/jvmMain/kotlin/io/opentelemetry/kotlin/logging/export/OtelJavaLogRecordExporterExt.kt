package io.opentelemetry.kotlin.logging.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter

@OptIn(ExperimentalApi::class)
public fun OtelJavaLogRecordExporter.toOtelKotlinLogRecordExporter(): LogRecordExporter =
    OtelJavaLogRecordExporterAdapter(this)
