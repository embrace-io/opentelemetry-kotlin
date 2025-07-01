package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordProcessor
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteLogRecord

internal class InMemoryLogRecordProcessor(
    private val exporter: InMemoryLogRecordExporter
) : OtelJavaLogRecordProcessor {

    override fun onEmit(context: OtelJavaContext, logRecord: OtelJavaReadWriteLogRecord) {
        exporter.export(mutableListOf(logRecord.toLogRecordData()))
    }
}
