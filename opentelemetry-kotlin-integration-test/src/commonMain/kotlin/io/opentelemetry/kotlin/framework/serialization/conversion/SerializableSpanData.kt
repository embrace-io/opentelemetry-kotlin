package io.opentelemetry.kotlin.framework.serialization.conversion

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.framework.serialization.SerializableSpanData
import io.opentelemetry.kotlin.tracing.data.SpanData
import io.opentelemetry.kotlin.framework.serialization.conversion.toSerializable

@OptIn(ExperimentalApi::class)
fun SpanData.toSerializable() =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableSpanData(
        name = name,
        kind = spanKind.name,
        statusData = status.toSerializable(),
        spanContext = spanContext.toSerializable(),
        parentSpanContext = parent.toSerializable(),
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
