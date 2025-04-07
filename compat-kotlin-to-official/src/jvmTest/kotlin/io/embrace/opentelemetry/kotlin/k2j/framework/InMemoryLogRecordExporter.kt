package io.embrace.opentelemetry.kotlin.k2j.framework

import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.logs.data.LogRecordData
import io.opentelemetry.sdk.logs.export.LogRecordExporter

internal class InMemoryLogRecordExporter : LogRecordExporter {

    private val impl = mutableListOf<LogRecordData>()

    val exportedLogRecords: List<LogRecordData>
        get() = impl

    override fun export(logs: MutableCollection<LogRecordData>): CompletableResultCode {
        impl.addAll(logs)
        return CompletableResultCode.ofSuccess()
    }

    override fun flush(): CompletableResultCode = CompletableResultCode.ofSuccess()
    override fun shutdown(): CompletableResultCode = CompletableResultCode.ofSuccess()
}
