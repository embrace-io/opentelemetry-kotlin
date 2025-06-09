package io.embrace.opentelemetry.kotlin.telescope.telemetry

import android.util.Log
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.Tracer

@OptIn(ExperimentalApi::class)
class SessionTelemetry(
    private val tracer: Tracer
) {
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
} 