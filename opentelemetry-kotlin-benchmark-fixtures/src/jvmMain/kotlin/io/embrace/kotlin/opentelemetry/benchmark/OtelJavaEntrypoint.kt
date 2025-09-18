package io.embrace.kotlin.opentelemetry.benchmark

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk

fun createOtelJavaOpenTelemetry(): OtelJavaOpenTelemetry = OtelJavaOpenTelemetrySdk.builder().build()
