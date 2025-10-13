package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.OtlpClient
import io.embrace.opentelemetry.kotlin.export.TelemetryExporter
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

@OptIn(ExperimentalApi::class)
internal class OtlpHttpLogRecordExporter(
    private val otlpClient: OtlpClient,
    initialDelayMs: Long,
    maxAttemptIntervalMs: Long,
    maxAttempts: Int,
) : LogRecordExporter {

    private val exporter = TelemetryExporter(initialDelayMs, maxAttemptIntervalMs, maxAttempts) {
        otlpClient.exportLogs(it).also { response ->
            println("OTLP exported log: $response")
        }
    }

    override fun export(telemetry: List<ReadableLogRecord>): OperationResultCode {
        return exporter.export(telemetry)
    }

    override fun forceFlush(): OperationResultCode = exporter.forceFlush()
    override fun shutdown(): OperationResultCode = exporter.shutdown()
}
