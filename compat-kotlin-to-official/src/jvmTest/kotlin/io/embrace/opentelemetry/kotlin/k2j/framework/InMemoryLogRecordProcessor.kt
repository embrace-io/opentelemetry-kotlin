package io.embrace.opentelemetry.kotlin.k2j.framework

import io.opentelemetry.context.Context
import io.opentelemetry.sdk.logs.LogRecordProcessor
import io.opentelemetry.sdk.logs.ReadWriteLogRecord

internal class InMemoryLogRecordProcessor(
    private val exporter: InMemoryLogRecordExporter
) : LogRecordProcessor {

    override fun onEmit(context: Context, logRecord: ReadWriteLogRecord) {
        exporter.export(mutableListOf(logRecord.toLogRecordData()))
    }
}
