package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.toProtobuf
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest
import io.opentelemetry.proto.logs.v1.ResourceLogs
import io.opentelemetry.proto.logs.v1.ScopeLogs

@OptIn(ExperimentalApi::class)
public fun List<ReadableLogRecord>.toProtobufByteArray() : ByteArray =
    ExportLogsServiceRequest.ADAPTER.encode(toExportLogsServiceRequest())

@OptIn(ExperimentalApi::class)
private fun List<ReadableLogRecord>.toExportLogsServiceRequest(): ExportLogsServiceRequest = ExportLogsServiceRequest(
    resource_logs = toResourceLogs()
)

@OptIn(ExperimentalApi::class)
private fun List<ReadableLogRecord>.toResourceLogs(): List<ResourceLogs> = map { it.toResourceLogs() }

@OptIn(ExperimentalApi::class)
private fun ReadableLogRecord.toResourceLogs(): ResourceLogs = ResourceLogs(
    scope_logs = listOf(toScopeLogs()),
    resource = resource.toProtobuf()
)

@OptIn(ExperimentalApi::class)
private fun ReadableLogRecord.toScopeLogs(): ScopeLogs = ScopeLogs(
    log_records = listOf(toProtobuf()),
    scope = instrumentationScopeInfo.toProtobuf(),
    schema_url = instrumentationScopeInfo.schemaUrl ?: ""
)



