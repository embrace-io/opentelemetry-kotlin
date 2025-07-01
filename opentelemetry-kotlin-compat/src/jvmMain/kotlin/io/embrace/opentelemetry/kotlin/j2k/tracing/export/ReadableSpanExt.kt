package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.opentelemetry.sdk.trace.data.SpanData

@Suppress("UnusedReceiverParameter")
@OptIn(ExperimentalApi::class)
internal fun ReadableSpan.toSpanData(): SpanData {
    throw UnsupportedOperationException()
}
