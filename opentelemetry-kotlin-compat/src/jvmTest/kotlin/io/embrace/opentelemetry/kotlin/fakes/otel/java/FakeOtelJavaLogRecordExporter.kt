package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaCompletableResultCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter

internal class FakeOtelJavaLogRecordExporter : OtelJavaLogRecordExporter {

    var flushCount = 0
    var shutdownCount = 0
    val exports: MutableList<OtelJavaLogRecordData> = mutableListOf()

    override fun export(logs: MutableCollection<OtelJavaLogRecordData>): OtelJavaCompletableResultCode {
        exports += logs
        return OtelJavaCompletableResultCode.ofSuccess()
    }

    override fun flush(): OtelJavaCompletableResultCode {
        flushCount += 1
        return OtelJavaCompletableResultCode.ofSuccess()
    }

    override fun shutdown(): OtelJavaCompletableResultCode {
        shutdownCount += 1
        return OtelJavaCompletableResultCode.ofSuccess()
    }
}
