package io.embrace.opentelemetry.example.compat

import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.trace.data.SpanData
import io.opentelemetry.sdk.trace.export.SpanExporter

internal class ExampleSpanExporter : SpanExporter {

    override fun export(spans: MutableCollection<SpanData>): CompletableResultCode {
        spans.forEach { span ->
            println("Exporting span: $span")
        }
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode = CompletableResultCode.ofSuccess()

    override fun shutdown(): CompletableResultCode = CompletableResultCode.ofSuccess()
}