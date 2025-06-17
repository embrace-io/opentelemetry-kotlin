package io.embrace.opentelemetry.kotlin.testing.common

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaCompletableResultCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanExporter

internal class InMemorySpanExporter : OtelJavaSpanExporter {
    private val impl = mutableListOf<OtelJavaSpanData>()

    internal val exportedSpans: List<OtelJavaSpanData>
        get() = impl

    internal fun reset() {
        impl.clear()
    }

    override fun export(spans: MutableCollection<OtelJavaSpanData>): OtelJavaCompletableResultCode {
        impl.addAll(spans)
        return OtelJavaCompletableResultCode.ofSuccess()
    }

    override fun flush(): OtelJavaCompletableResultCode = OtelJavaCompletableResultCode.ofSuccess()
    override fun shutdown(): OtelJavaCompletableResultCode = OtelJavaCompletableResultCode.ofSuccess()
}
