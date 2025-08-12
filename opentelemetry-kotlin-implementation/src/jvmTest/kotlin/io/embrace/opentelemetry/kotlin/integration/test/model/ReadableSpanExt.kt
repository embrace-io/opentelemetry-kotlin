package io.embrace.opentelemetry.kotlin.integration.test.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableEventData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableLinkData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableResource
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanContext
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal fun ReadableSpan.toSerializable(): SerializableSpanData = SerializableSpanData(
    name = name,
    kind = spanKind.name,
    statusData = status.toSerializable(),
    spanContext = SerializableSpanContext("", "", "", emptyMap()),
    parentSpanContext = SerializableSpanContext("", "", "", emptyMap()),
    startTimestamp = startTimestamp,
    attributes = attributes.toSerializable(),
    events = events.map(EventData::toSerializable),
    links = links.map(LinkData::toSerializable),
    endTimestamp = endTimestamp ?: -1,
    ended = hasEnded,
    totalRecordedEvents = events.size,
    totalRecordedLinks = links.size,
    totalAttributeCount = attributes.size,
    resource = resource.toSerializable(),
    instrumentationScopeInfo = instrumentationScopeInfo.toSerializable(),
)

private fun Map<String, Any>.toSerializable(): Map<String, String> =
    mapValues { it.value.toString() }

@OptIn(ExperimentalApi::class)
private fun StatusData.toSerializable(): SerializableSpanStatusData =
    SerializableSpanStatusData(statusCode.name, description.toString())

@OptIn(ExperimentalApi::class)
private fun Resource.toSerializable(): SerializableResource = SerializableResource(
    schemaUrl.toString(),
    attributes.toSerializable()
)

@OptIn(ExperimentalApi::class)
private fun InstrumentationScopeInfo.toSerializable(): SerializableInstrumentationScopeInfo =
    SerializableInstrumentationScopeInfo(
        name,
        version.toString(),
        schemaUrl.toString(),
        attributes.toSerializable()
    )

@OptIn(ExperimentalApi::class)
private fun EventData.toSerializable(): SerializableEventData = SerializableEventData(
    name,
    attributes.toSerializable(),
    timestamp,
    attributes.size
)

@OptIn(ExperimentalApi::class)
private fun LinkData.toSerializable(): SerializableLinkData = SerializableLinkData(
    spanContext.toSerializable(),
    attributes.toSerializable(),
    attributes.size,
)

@OptIn(ExperimentalApi::class)
private fun SpanContext.toSerializable(): SerializableSpanContext = SerializableSpanContext(
    traceId,
    spanId,
    traceFlags.hex,
    traceState.asMap()
)
