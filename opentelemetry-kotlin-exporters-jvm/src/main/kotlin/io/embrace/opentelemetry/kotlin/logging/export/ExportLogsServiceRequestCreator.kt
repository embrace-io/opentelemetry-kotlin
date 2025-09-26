package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.toProtobuf
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest
import io.opentelemetry.proto.collector.logs.v1.exportLogsServiceRequest
import io.opentelemetry.proto.logs.v1.ResourceLogs
import io.opentelemetry.proto.logs.v1.ScopeLogs
import io.opentelemetry.proto.logs.v1.resourceLogs
import io.opentelemetry.proto.logs.v1.scopeLogs

@OptIn(ExperimentalApi::class)
fun List<ReadableLogRecord>.toExportLogsServiceRequest(): ExportLogsServiceRequest {
    return exportLogsServiceRequest {
        resourceLogs.addAll(map(::createResourceLogs))
    }
}

@OptIn(ExperimentalApi::class)
private fun createResourceLogs(record: ReadableLogRecord): ResourceLogs = resourceLogs {
    scopeLogs.add(createScopeLogs(record))
    resource = record.resource.toProtobuf()
}

@OptIn(ExperimentalApi::class)
private fun createScopeLogs(record: ReadableLogRecord): ScopeLogs = scopeLogs {
    logRecords.add(record.toProtobuf())
    scope = record.instrumentationScopeInfo.toProtobuf()
    record.instrumentationScopeInfo.schemaUrl?.let {
        schemaUrl = it
    }
}
