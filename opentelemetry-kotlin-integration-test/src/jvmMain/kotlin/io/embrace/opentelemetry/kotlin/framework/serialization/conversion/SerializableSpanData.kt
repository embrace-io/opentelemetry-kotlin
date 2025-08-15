package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
fun SpanData.toSerializable(sanitizeSpanContextIds: Boolean = true) = SerializableSpanData(
    name = name,
    kind = spanKind.name,
    statusData = status.toSerializable(),
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    parentSpanContext = parent.toSerializable(sanitizeSpanContextIds),
    startTimestamp = startTimestamp,
    attributes = attributes.toSerializable(),
    events = events.map { it.toSerializable() },
    links = links.map { it.toSerializable() },
    endTimestamp = endTimestamp ?: 0,
    ended = hasEnded,
    totalRecordedEvents = events.size,
    totalRecordedLinks = links.size,
    totalAttributeCount = attributes.size,
    resource = resource.toSerializable(),
    instrumentationScopeInfo = instrumentationScopeInfo.toSerializable()
)
