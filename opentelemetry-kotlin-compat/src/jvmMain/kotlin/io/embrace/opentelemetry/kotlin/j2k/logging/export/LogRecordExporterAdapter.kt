package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordExporter
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.j2k.toOperationResultCode
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordExporter
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

@OptIn(ExperimentalApi::class)
public class LogRecordExporterAdapter(
    private val impl: OtelJavaLogRecordExporter
) : LogRecordExporter {

    override fun export(telemetry: List<ReadableLogRecord>): OperationResultCode {
        val code = impl.export(telemetry.map(ReadableLogRecord::toLogRecordData))
        return code.toOperationResultCode()
    }

    override fun shutdown(): OperationResultCode = impl.shutdown().toOperationResultCode()
    override fun forceFlush(): OperationResultCode = impl.flush().toOperationResultCode()
}
