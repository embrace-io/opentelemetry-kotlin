package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.StatusCode

public fun main(tracerProvider: TracerProvider) {
    val tracer: Tracer = tracerProvider.getTracer("com.example.app", "1.0.0")
    tracer.createSpan(
        name = "my_span",
        status = StatusCode.Error("Failed for some reason"),
        spanKind = SpanKind.INTERNAL,
        parent = null,
        startTimestampMs = 0
    ) {
        setBooleanAttribute("my_key", true)

        addEvent("my_event") {
            this@createSpan.setStringAttribute("span_key", "span_value")
            setStringAttribute("event_key", "event_value")
        }
        addEvent("previous_event", 100) {
            setStringAttribute("previous_key", "previous_value")
        }
    }

    tracer.createSpan("Foo") {
        setStringAttribute("key", "value")
    }.use {
        it.status = StatusCode.Ok
        it.end()
    }
}
