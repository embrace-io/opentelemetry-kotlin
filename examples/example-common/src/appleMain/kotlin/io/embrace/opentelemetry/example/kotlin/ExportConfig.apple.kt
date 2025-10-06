@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.example.ExampleLogRecordProcessor
import io.embrace.opentelemetry.example.ExampleSpanProcessor
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

actual fun createSpanProcessor(url: String?): SpanProcessor = ExampleSpanProcessor()

actual fun createLogRecordProcessor(url: String?): LogRecordProcessor = ExampleLogRecordProcessor()
