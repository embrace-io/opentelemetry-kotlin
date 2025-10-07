package io.embrace.opentelemetry.kotlin.tracing.export

import com.google.protobuf.ByteString
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.createKeyValues
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.opentelemetry.proto.trace.v1.Span
import io.opentelemetry.proto.trace.v1.SpanKt.event
import io.opentelemetry.proto.trace.v1.SpanKt.link
import io.opentelemetry.proto.trace.v1.span
import io.opentelemetry.proto.trace.v1.status

@OptIn(ExperimentalApi::class)
fun SpanData.toProtobuf(): Span {
    val spanData = this
    return span {
        name = spanData.name
        traceId = ByteString.copyFrom(spanData.spanContext.traceIdBytes)
        spanId = ByteString.copyFrom(spanData.spanContext.spanIdBytes)
        startTimeUnixNano = spanData.startTimestamp
        endTimeUnixNano = spanData.endTimestamp ?: 0
        attributes.addAll(spanData.attributes.createKeyValues())

        status = status {
            spanData.status.description?.let {
                message = it
            }
            codeValue = spanData.status.statusCode.ordinal
        }
        events.addAll(spanData.events.map(::convertEvent))
        links.addAll(spanData.links.map(::convertLink))
    }
}

@OptIn(ExperimentalApi::class)
private fun convertEvent(data: EventData): Span.Event = event {
    name = data.name
    timeUnixNano = data.timestamp
    attributes.addAll(data.attributes.createKeyValues())
}

@OptIn(ExperimentalApi::class)
private fun convertLink(data: LinkData): Span.Link = link {
    traceId = ByteString.copyFrom(data.spanContext.traceIdBytes)
    spanId = ByteString.copyFrom(data.spanContext.spanIdBytes)
    attributes.addAll(data.attributes.createKeyValues())
}
