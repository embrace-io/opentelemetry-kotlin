package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

@OptIn(ExperimentalApi::class)
fun main() {
    val endpoint = null // supply a URL string here to export telemetry
    val otelApi: OpenTelemetry = instantiateOtelApi(endpoint)
    runTracingExamples(otelApi)
    runLoggingExamples(otelApi)
    Thread.sleep(1000)
}
