package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

@OptIn(ExperimentalApi::class)
internal class InMemoryLogRecordProcessor(
    private val exporter: InMemoryLogRecordExporter
) : LogRecordProcessor {

    override fun onEmit(log: ReadWriteLogRecord, context: Context) {
        exporter.export(mutableListOf(log))
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
