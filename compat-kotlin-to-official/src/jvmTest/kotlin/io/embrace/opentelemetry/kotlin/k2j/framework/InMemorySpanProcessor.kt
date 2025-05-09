package io.embrace.opentelemetry.kotlin.k2j.framework

import io.opentelemetry.context.Context
import io.opentelemetry.sdk.trace.ReadWriteSpan
import io.opentelemetry.sdk.trace.ReadableSpan
import io.opentelemetry.sdk.trace.SpanProcessor

internal class InMemorySpanProcessor(private val exporter: InMemorySpanExporter) : SpanProcessor {
    override fun onStart(parentContext: Context, span: ReadWriteSpan) {
    }

    override fun isStartRequired(): Boolean = true

    override fun onEnd(span: ReadableSpan) {
        exporter.export(mutableListOf(span.toSpanData()))
    }

    override fun isEndRequired(): Boolean = true
}
