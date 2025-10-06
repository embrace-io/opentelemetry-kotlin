package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.OtlpClient
import io.embrace.opentelemetry.kotlin.export.TelemetryExporter
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
internal class OtlpHttpSpanExporter(
    private val otlpClient: OtlpClient,
    initialDelayMs: Long,
    maxAttemptIntervalMs: Long,
    maxAttempts: Int,
) : SpanExporter {

    private val exporter = TelemetryExporter(initialDelayMs, maxAttemptIntervalMs, maxAttempts) {
        otlpClient.exportTraces(it).also { response ->
            println("OTLP exported trace: $response")
        }
    }

    override fun export(telemetry: List<SpanData>): OperationResultCode {
        return exporter.export(telemetry)
    }

    override fun forceFlush(): OperationResultCode = exporter.forceFlush()
    override fun shutdown(): OperationResultCode = exporter.shutdown()
}
