package io.embrace.opentelemetry.kotlin.testing.common

import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.trace.data.SpanData
import io.opentelemetry.sdk.trace.export.SpanExporter

public class InMemorySpanExporter() : SpanExporter {
    private val impl = mutableListOf<SpanData>()

    public val exportedSpans: List<SpanData>
        get() = impl

    public fun reset() {
        impl.clear()
    }

    override fun export(spans: MutableCollection<SpanData>): CompletableResultCode {
        impl.addAll(spans)
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode = CompletableResultCode.ofSuccess()
    override fun shutdown(): CompletableResultCode = CompletableResultCode.ofSuccess()
}