package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.toExportLogsServiceRequest
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.export.toExportTraceServiceRequest
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceResponse
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceResponse

@OptIn(ExperimentalApi::class)
internal actual fun List<SpanData>.serializeSpanData(): ByteArray {
    return toExportTraceServiceRequest().toByteArray()
}

@OptIn(ExperimentalApi::class)
internal actual fun List<ReadableLogRecord>.serializeLogRecordData(): ByteArray {
    return toExportLogsServiceRequest().toByteArray()
}

internal actual fun ByteArray.deserializeTracesErrorMessage(): String? {
    val response = ExportTraceServiceResponse.parseFrom(this)
    return response.partialSuccess.errorMessage
}

internal actual fun ByteArray.deserializeLogRecordErrorMessage(): String? {
    val response = ExportLogsServiceResponse.parseFrom(this)
    return response.partialSuccess.errorMessage
}
