package io.embrace.opentelemetry.example.java

import io.embrace.opentelemetry.example.ExampleLogRecordProcessor
import io.embrace.opentelemetry.example.ExampleSpanProcessor
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.trace.SdkTracerProvider

class OtelJavaApi {

    private val otel: OpenTelemetrySdk = OpenTelemetrySdk.builder()
        .setTracerProvider(SdkTracerProvider.builder().addSpanProcessor(ExampleSpanProcessor()).build())
        .setLoggerProvider(SdkLoggerProvider.builder().addLogRecordProcessor(ExampleLogRecordProcessor()).build())
        .build()
    private val instrumentationScopeName = "com.example.app"

    val tracer = otel.tracerProvider.get(instrumentationScopeName)
    val logger = otel.sdkLoggerProvider.get(instrumentationScopeName)
}