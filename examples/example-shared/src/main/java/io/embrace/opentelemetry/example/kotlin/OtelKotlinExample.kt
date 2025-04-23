package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.example.ExampleLogRecordProcessor
import io.embrace.opentelemetry.example.ExampleSpanProcessor
import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.k2j.creation.CompatSdkFactory
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.trace.SdkTracerProvider

@OptIn(ExperimentalApi::class)
private fun instantiateOtelApi(): OpenTelemetry {
    val otelJava = io.opentelemetry.sdk.OpenTelemetrySdk.builder()
        .setTracerProvider(SdkTracerProvider.builder().addSpanProcessor(ExampleSpanProcessor()).build())
        .setLoggerProvider(SdkLoggerProvider.builder().addLogRecordProcessor(ExampleLogRecordProcessor()).build())
        .build()
    return OpenTelemetrySdk(otelJava)
}

@OptIn(ExperimentalApi::class)
fun createInstrumentationWithOtelKotlin() {
    val overrideClock: Clock = object : Clock {
        override fun now(): Long = System.currentTimeMillis()
    }
    CompatSdkFactory().createOpenTelemetry {
        clock = overrideClock

        loggerProviderParams.clock = overrideClock
        loggerProviderParams {
            clock = overrideClock
        }
        tracerProviderParams { }
    }
    val api = instantiateOtelApi()
    val tracer = api.tracerProvider.getTracer(
        name = "kotlin-example-app",
        version = "0.1.0"
    )
    tracer.createSpan("my_span").end()
    api.loggerProvider.getLogger("my_logger").log("Hello, World!")
}

