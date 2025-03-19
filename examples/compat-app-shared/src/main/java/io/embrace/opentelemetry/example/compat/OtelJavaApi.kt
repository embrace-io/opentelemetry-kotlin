package io.embrace.opentelemetry.example.compat

import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.trace.SdkTracerProvider

class OtelJavaApi {

    private val otel: OpenTelemetrySdk = OpenTelemetrySdk.builder()
        .setTracerProvider(SdkTracerProvider.builder().addSpanProcessor(io.embrace.opentelemetry.example.compat.ExampleSpanProcessor()).build())
        .setLoggerProvider(SdkLoggerProvider.builder().addLogRecordProcessor(io.embrace.opentelemetry.example.compat.ExampleLogRecordProcessor()).build())
        .build()
    private val instrumentationScopeName = "com.example.app"

    val tracer = otel.tracerProvider.get(instrumentationScopeName)
    val logger = otel.sdkLoggerProvider.get(instrumentationScopeName)
}