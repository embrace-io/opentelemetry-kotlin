package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordExporter
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

@OptIn(ExperimentalApi::class)
internal class InMemoryLogRecordExporter : LogRecordExporter {

    private val impl = mutableListOf<ReadableLogRecord>()

    val exportedLogRecords: List<ReadableLogRecord>
        get() = impl

    override fun export(telemetry: List<ReadableLogRecord>): OperationResultCode {
        impl += telemetry
        return OperationResultCode.Success
    }

    override fun shutdown(): OperationResultCode = OperationResultCode.Success
    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
}
