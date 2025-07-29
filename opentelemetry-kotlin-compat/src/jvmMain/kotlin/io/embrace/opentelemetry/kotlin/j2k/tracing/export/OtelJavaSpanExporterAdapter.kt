package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanExporter
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.j2k.toOperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.export.SpanExporter
import toOtelJavaSpanData

@OptIn(ExperimentalApi::class)
public class OtelJavaSpanExporterAdapter(
    private val impl: OtelJavaSpanExporter
) : SpanExporter {

    override fun export(telemetry: List<SpanData>): OperationResultCode {
        val code = impl.export(telemetry.map(SpanData::toOtelJavaSpanData))
        return code.toOperationResultCode()
    }

    override fun shutdown(): OperationResultCode = impl.shutdown().toOperationResultCode()
    override fun forceFlush(): OperationResultCode = impl.flush().toOperationResultCode()
}
