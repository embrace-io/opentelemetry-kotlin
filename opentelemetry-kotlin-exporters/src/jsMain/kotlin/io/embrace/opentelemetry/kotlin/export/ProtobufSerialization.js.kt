package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
internal actual fun List<SpanData>.serializeSpanData(): ByteArray {
    throw UnsupportedOperationException()
}

@OptIn(ExperimentalApi::class)
internal actual fun List<ReadableLogRecord>.serializeLogRecordData(): ByteArray {
    throw UnsupportedOperationException()
}

internal actual fun ByteArray.deserializeTracesErrorMessage(): String? {
    throw UnsupportedOperationException()
}

internal actual fun ByteArray.deserializeLogRecordErrorMessage(): String? {
    throw UnsupportedOperationException()
}
