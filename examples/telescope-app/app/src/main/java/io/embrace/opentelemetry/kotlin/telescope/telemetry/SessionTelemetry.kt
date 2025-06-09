package io.embrace.opentelemetry.kotlin.telescope.telemetry

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
        if (currentDestination == destination) return
        currentChildSpan?.end()
        currentDestination = destination
        currentChildSpan = createSpan("Navigation to $destination", parentSpan)
    }

    private fun createSpan(name: String, parent: Span? = null): Span {
        return tracer.createSpan(name, parent?.spanContext)
    }
} 