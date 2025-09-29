package io.embrace.opentelemetry.example.kotlin

import android.app.Application
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

@OptIn(ExperimentalApi::class)
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val endpoint = null // supply a URL string here to export telemetry
        val otelApi: OpenTelemetry = instantiateOtelApi(endpoint)
        runTracingExamples(otelApi)
        runLoggingExamples(otelApi)
    }
}
