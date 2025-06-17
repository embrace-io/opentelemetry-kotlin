package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaCompletableResultCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter

internal class InMemoryLogRecordExporter : OtelJavaLogRecordExporter {

    private val impl = mutableListOf<OtelJavaLogRecordData>()

    val exportedLogRecords: List<OtelJavaLogRecordData>
        get() = impl

    override fun export(logs: MutableCollection<OtelJavaLogRecordData>): OtelJavaCompletableResultCode {
        impl.addAll(logs)
        return OtelJavaCompletableResultCode.ofSuccess()
    }

    override fun flush(): OtelJavaCompletableResultCode = OtelJavaCompletableResultCode.ofSuccess()
    override fun shutdown(): OtelJavaCompletableResultCode = OtelJavaCompletableResultCode.ofSuccess()
}
