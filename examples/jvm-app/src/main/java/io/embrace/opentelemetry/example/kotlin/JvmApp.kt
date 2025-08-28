package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

@OptIn(ExperimentalApi::class)
fun main() {
    val otelApi: OpenTelemetry = instantiateOtelApi()
    runTracingExamples(otelApi)
    runLoggingExamples(otelApi)
}
