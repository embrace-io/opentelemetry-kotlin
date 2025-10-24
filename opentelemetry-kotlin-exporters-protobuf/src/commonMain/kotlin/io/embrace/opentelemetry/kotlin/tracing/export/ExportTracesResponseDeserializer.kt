package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceResponse

@OptIn(ExperimentalApi::class)
fun ByteArray.deserializeTraceRecordErrorMessage() =
    ExportLogsServiceResponse.ADAPTER.decode(this).partial_success?.error_message