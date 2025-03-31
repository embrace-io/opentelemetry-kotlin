package io.embrace.opentelemetry.example

import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.logs.data.LogRecordData
import io.opentelemetry.sdk.logs.export.LogRecordExporter

internal class ExampleLogRecordExporter : LogRecordExporter {

    override fun export(logs: MutableCollection<LogRecordData>): CompletableResultCode {
        logs.forEach {
            println("Exporting log: $it")
        }
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode = CompletableResultCode.ofSuccess()
    override fun shutdown(): CompletableResultCode = CompletableResultCode.ofSuccess()
}
