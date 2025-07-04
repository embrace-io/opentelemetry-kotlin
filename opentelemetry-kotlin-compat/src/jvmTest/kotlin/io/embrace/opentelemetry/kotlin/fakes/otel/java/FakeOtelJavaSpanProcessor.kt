package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanProcessor
import io.opentelemetry.context.Context
import io.opentelemetry.sdk.trace.ReadWriteSpan
import io.opentelemetry.sdk.trace.ReadableSpan

internal class FakeOtelJavaSpanProcessor : OtelJavaSpanProcessor {

    val startSpans: MutableList<ReadWriteSpan> = mutableListOf()
    val endSpans: MutableList<ReadableSpan> = mutableListOf()

    override fun onStart(parentContext: Context, span: ReadWriteSpan) {
        startSpans.add(span)
    }

    override fun onEnd(span: ReadableSpan) {
        endSpans.add(span)
    }

    override fun isStartRequired(): Boolean = true

    override fun isEndRequired(): Boolean = true
}
