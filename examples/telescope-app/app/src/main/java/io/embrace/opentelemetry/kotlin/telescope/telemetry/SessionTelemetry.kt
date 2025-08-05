package io.embrace.opentelemetry.kotlin.telescope.telemetry

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.toOtelKotlinContext
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.ext.storeInContext
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.opentelemetry.context.Context

@OptIn(ExperimentalApi::class)
class SessionTelemetry(
    private val tracer: Tracer,
) {
    private var parentSpan: Span? = null
    private var currentChildSpan: Span? = null
    private var currentDestination = ""
    private val context = Context.root()

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
        return tracer.createSpan(name, parent?.storeInContext(context.toOtelKotlinContext()))
    }
} 