@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

/**
 * Creates a [SpanProcessor]. If a URL is supplied and the platform is the JVM/Android, an OTLP
 * HTTP exporter will be created - otherwise, telemetry will be printed to stdout.
 */
expect fun createSpanProcessor(url: String?): SpanProcessor

/**
 * Creates a [LogRecordProcessor]. If a URL is supplied and the platform is the JVM/Android, an OTLP
 * HTTP exporter will be created - otherwise, telemetry will be printed to stdout.
 */
expect fun createLogRecordProcessor(url: String?): LogRecordProcessor
