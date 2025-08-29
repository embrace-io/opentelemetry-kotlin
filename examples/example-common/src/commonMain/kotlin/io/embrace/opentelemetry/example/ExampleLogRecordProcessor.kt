package io.embrace.opentelemetry.example

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

@OptIn(ExperimentalApi::class)
internal class ExampleLogRecordProcessor : LogRecordProcessor {

    private val exporter: ExampleLogRecordExporter = ExampleLogRecordExporter()

    override fun onEmit(
        log: ReadWriteLogRecord,
        context: Context
    ) {
        exporter.export(listOf(log))
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
