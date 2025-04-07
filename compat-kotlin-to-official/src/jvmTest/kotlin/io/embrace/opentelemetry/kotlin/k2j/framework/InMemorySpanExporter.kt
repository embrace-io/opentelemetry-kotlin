package io.embrace.opentelemetry.kotlin.k2j.framework

import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.trace.data.SpanData
import io.opentelemetry.sdk.trace.export.SpanExporter

internal class InMemorySpanExporter : SpanExporter {

    private val impl = mutableListOf<SpanData>()

    val exportedSpans: List<SpanData>
        get() = impl

    override fun export(spans: MutableCollection<SpanData>): CompletableResultCode {
        impl.addAll(spans)
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode = CompletableResultCode.ofSuccess()
    override fun shutdown(): CompletableResultCode = CompletableResultCode.ofSuccess()
}
