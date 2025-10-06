package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
internal expect fun List<SpanData>.serializeSpanData(): ByteArray

@OptIn(ExperimentalApi::class)
internal expect fun List<ReadableLogRecord>.serializeLogRecordData(): ByteArray

internal expect fun ByteArray.deserializeTracesErrorMessage(): String?

internal expect fun ByteArray.deserializeLogRecordErrorMessage(): String?
