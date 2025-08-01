package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter

@OptIn(ExperimentalApi::class)
public fun OtelJavaLogRecordExporter.toOtelKotlinLogRecordExporter(): LogRecordExporter =
    OtelJavaLogRecordExporterAdapter(this)
