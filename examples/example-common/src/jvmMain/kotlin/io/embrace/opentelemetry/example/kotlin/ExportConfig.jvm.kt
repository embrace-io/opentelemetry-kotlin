@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.example.ExampleLogRecordProcessor
import io.embrace.opentelemetry.example.ExampleSpanProcessor
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.export.SimpleLogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.export.createOtlpHttpLogRecordExporter
import io.embrace.opentelemetry.kotlin.tracing.export.SimpleSpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.createOtlpHttpSpanExporter

actual fun createSpanProcessor(url: String?): SpanProcessor = when (url) {
    null -> ExampleSpanProcessor()
    else -> SimpleSpanProcessor(createOtlpHttpSpanExporter(url))
}

actual fun createLogRecordProcessor(url: String?): LogRecordProcessor = when (url) {
    null -> ExampleLogRecordProcessor()
    else -> SimpleLogRecordProcessor(createOtlpHttpLogRecordExporter(url))
}
