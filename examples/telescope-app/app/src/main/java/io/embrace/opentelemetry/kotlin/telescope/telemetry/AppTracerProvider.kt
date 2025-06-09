package io.embrace.opentelemetry.kotlin.telescope.telemetry

import android.content.res.Resources
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.semconv.ServiceAttributes

@OptIn(ExperimentalApi::class)
class AppTracerProvider(
    private val resources: Resources,
    private val endpoint: String = "http://10.0.2.2:4317", // Default to emulator localhost
    private val serviceName: String = "My telescopes"
) {
    val tracer: Tracer by lazy { createTracer() }

    private fun createTracer(): Tracer {
        val exporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint(endpoint)
            .build()

        val resource = Resource.getDefault().toBuilder()
            .put(ServiceAttributes.SERVICE_NAME, serviceName)
            .put("device.screen_resolution", getScreenResolution())
            .build()

        val sdkTracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
            .setResource(resource)
            .build()

        return TracerProviderAdapter(sdkTracerProvider).getTracer("AppTracer")
    }

    private fun getScreenResolution() = "${resources.displayMetrics.widthPixels}x${resources.displayMetrics.heightPixels}"
}