package io.embrace.opentelemetry.example

import io.opentelemetry.context.Context
import io.opentelemetry.sdk.trace.ReadWriteSpan
import io.opentelemetry.sdk.trace.ReadableSpan
import io.opentelemetry.sdk.trace.SpanProcessor

internal class ExampleSpanProcessor : SpanProcessor {

    private val exporter: ExampleSpanExporter =
        ExampleSpanExporter()

    override fun onStart(parentContext: Context, span: ReadWriteSpan) {
    }

    override fun isStartRequired(): Boolean = true

    override fun onEnd(span: ReadableSpan) {
        exporter.export(mutableListOf(span.toSpanData()))
    }

    override fun isEndRequired(): Boolean = true
}