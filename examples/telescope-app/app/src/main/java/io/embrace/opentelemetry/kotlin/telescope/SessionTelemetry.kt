package io.embrace.opentelemetry.kotlin.telescope

import android.content.res.Resources
import android.util.Log
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.semconv.ServiceAttributes

@OptIn(ExperimentalApi::class)
class SessionTelemetry(private val resources: Resources) {

    private val tracerProviderAdapter = getTracerProviderAdapter()
    private val tracer = tracerProviderAdapter.getTracer("AppTracer")
    private var parentSpan: Span? = null
    private var currentChildSpan: Span? = null
    private var currentDestination = ""

    fun onAppStart() {
        parentSpan = createSpan("AppSession")
    }

    fun onAppStop() {
        currentChildSpan?.end()
        parentSpan?.end()
    }

    fun onNavigation(destination: String) {
        Log.w("SessionTelemetry", "Navigating to $destination, current: $currentDestination")
        if (currentDestination == destination) return
        currentChildSpan?.end()
        Log.w("SessionTelemetry", "Ended span for $currentDestination")
        currentDestination = destination
        currentChildSpan = createSpan("Navigation to $destination", parentSpan)
        Log.w("SessionTelemetry", "Started span for $destination")
    }

    private fun createSpan(name: String, parent: Span? = null): Span {
        return tracer.createSpan(name, parent?.spanContext)
    }

    @OptIn(ExperimentalApi::class)
    private fun getTracerProviderAdapter(): TracerProviderAdapter {
        val exporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint("http://10.0.2.2:4317") // Emulator localhost
            .build()

        // Resources that will be attached to telemetry to provide better context.
        // This is a good place to add information about the app, device, and OS.
        val resource = Resource.getDefault().toBuilder()
            .put(ServiceAttributes.SERVICE_NAME, "My telescopes")
            .put("device.screen_resolution", getDeviceScreenResolution())
            .build()

        // The tracer provider will create spans and export them to the configured span processors.
        // For now, we will use a simple span processor that logs the spans to the console.
        val sdkTracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
            .setResource(resource)
            .build()

        return TracerProviderAdapter(sdkTracerProvider)
    }

    private fun getDeviceScreenResolution() =
        "${resources.displayMetrics.widthPixels}x${resources.displayMetrics.heightPixels}"
}
