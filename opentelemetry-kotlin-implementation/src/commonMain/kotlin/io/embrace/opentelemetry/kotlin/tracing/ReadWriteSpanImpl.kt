package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanRelationships

@OptIn(ExperimentalApi::class)
public class ReadWriteSpanImpl(
    private val readableSpan: ReadableSpan,
    private val spanRelationships: SpanRelationships,
    override var name: String,
    override var status: StatusData,
    private val onEnd: (timestamp: Long?) -> Unit,
    private val recording: () -> Boolean
) : ReadWriteSpan, ReadableSpan by readableSpan, SpanRelationships by spanRelationships {

    override fun end() {
        onEnd(null)
    }

    override fun end(timestamp: Long) {
        onEnd(timestamp)
    }

    override fun isRecording(): Boolean = recording()
}
