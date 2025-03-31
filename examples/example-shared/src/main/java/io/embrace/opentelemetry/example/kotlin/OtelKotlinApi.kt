package io.embrace.opentelemetry.example.kotlin

import io.embrace.opentelemetry.example.ExampleLogRecordProcessor
import io.embrace.opentelemetry.example.ExampleSpanProcessor
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.trace.SdkTracerProvider

class OtelKotlinApi {

    private val otel: OpenTelemetrySdk = OpenTelemetrySdk.builder()
        .setTracerProvider(SdkTracerProvider.builder().addSpanProcessor(ExampleSpanProcessor()).build())
        .setLoggerProvider(SdkLoggerProvider.builder().addLogRecordProcessor(ExampleLogRecordProcessor()).build())
        .build()
    private val instrumentationScopeName = "com.example.app"

    val tracer = TracerProviderAdapter(otel.tracerProvider, ClockAdapter())
    val logger = otel.sdkLoggerProvider.get(instrumentationScopeName)
}