package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest
import io.opentelemetry.proto.collector.logs.v1.exportLogsServiceRequest
import io.opentelemetry.proto.logs.v1.resourceLogs
import io.opentelemetry.proto.logs.v1.scopeLogs

@OptIn(ExperimentalApi::class)
class OtlpHttpLogRecordExporter : LogRecordExporter {

    override fun export(telemetry: List<ReadableLogRecord>): OperationResultCode {
        telemetry.forEach { record ->
            buildRequest(record)
        }
        return OperationResultCode.Success
    }

    private fun buildRequest(record: ReadableLogRecord): ExportLogsServiceRequest {
        return exportLogsServiceRequest {
            resourceLogs {
                scopeLogs {
                    logRecords.add(record.toProtobuf())
                    scope
                    scope = record.instrumentationScopeInfo.toProtobuf()
                    record.instrumentationScopeInfo.schemaUrl?.let {
                        schemaUrl = it
                    }
                }
                resource = record.resource.toProtobuf()
            }
        }
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
