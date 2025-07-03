package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanExporter
import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.trace.data.SpanData

internal class FakeOtelJavaSpanExporter : OtelJavaSpanExporter {

    var flushCount = 0
    var shutdownCount = 0
    val exports: MutableList<SpanData> = mutableListOf()

    override fun export(logs: MutableCollection<SpanData>): CompletableResultCode {
        exports += logs
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode {
        flushCount += 1
        return CompletableResultCode.ofSuccess()
    }

    override fun shutdown(): CompletableResultCode {
        shutdownCount += 1
        return CompletableResultCode.ofSuccess()
    }
}
