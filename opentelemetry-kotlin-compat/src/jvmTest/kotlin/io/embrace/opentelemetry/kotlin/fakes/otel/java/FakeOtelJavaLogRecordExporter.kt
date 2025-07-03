package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter
import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.logs.data.LogRecordData

internal class FakeOtelJavaLogRecordExporter : OtelJavaLogRecordExporter {

    var flushCount = 0
    var shutdownCount = 0
    val exports: MutableList<LogRecordData> = mutableListOf()

    override fun export(logs: MutableCollection<LogRecordData>): CompletableResultCode {
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
