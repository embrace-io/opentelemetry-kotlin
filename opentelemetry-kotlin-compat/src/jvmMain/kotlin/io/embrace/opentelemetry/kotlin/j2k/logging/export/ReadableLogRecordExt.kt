package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.sdk.logs.data.LogRecordData

@Suppress("UnusedReceiverParameter")
@OptIn(ExperimentalApi::class)
internal fun ReadableLogRecord.toLogRecordData(): LogRecordData {
    throw UnsupportedOperationException()
}
