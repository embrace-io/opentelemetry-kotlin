package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordExporter

@OptIn(ExperimentalApi::class)
public fun OtelJavaLogRecordExporter.toOtelKotlin(): LogRecordExporter =
    OtelJavaLogRecordExporterAdapter(this)
