package io.embrace.opentelemetry.example

import io.opentelemetry.context.Context
import io.opentelemetry.sdk.logs.LogRecordProcessor
import io.opentelemetry.sdk.logs.ReadWriteLogRecord

internal class ExampleLogRecordProcessor : LogRecordProcessor {

    private val exporter: ExampleLogRecordExporter =
        ExampleLogRecordExporter()

    override fun onEmit(context: Context, logRecord: ReadWriteLogRecord) {
        exporter.export(mutableListOf(logRecord.toLogRecordData()))
    }
}
