package io.opentelemetry.kotlin.integration.test.model

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.kotlin.framework.serialization.SerializableEventData
import io.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo
import io.opentelemetry.kotlin.framework.serialization.SerializableLinkData
import io.opentelemetry.kotlin.framework.serialization.SerializableResource
import io.opentelemetry.kotlin.framework.serialization.SerializableSpanContext
import io.opentelemetry.kotlin.framework.serialization.SerializableSpanData
import io.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData
import io.opentelemetry.kotlin.resource.Resource
import io.opentelemetry.kotlin.tracing.data.EventData
import io.opentelemetry.kotlin.tracing.data.LinkData
import io.opentelemetry.kotlin.tracing.data.StatusData
import io.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.kotlin.integration.test.model.toSerializable

@OptIn(ExperimentalApi::class)
internal fun ReadableSpan.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableSpanData =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableSpanData(
        name = name,
        kind = spanKind.name,
        statusData = status.toSerializable(),
        spanContext = spanContext.toSerializable(),
        parentSpanContext = spanContext.toSerializable(),
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
private fun StatusData.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData(
        statusCode.name,
        description.toString()
    )

@OptIn(ExperimentalApi::class)
private fun Resource.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableResource =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableResource(
        schemaUrl.toString(),
        attributes.toSerializable()
    )

@OptIn(ExperimentalApi::class)
private fun InstrumentationScopeInfo.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo(
        name,
        version.toString(),
        schemaUrl.toString(),
        attributes.toSerializable()
    )

@OptIn(ExperimentalApi::class)
private fun EventData.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableEventData =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableEventData(
        name,
        attributes.toSerializable(),
        timestamp,
        attributes.size
    )

@OptIn(ExperimentalApi::class)
private fun LinkData.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableLinkData =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableLinkData(
        spanContext.toSerializable(),
        attributes.toSerializable(),
        attributes.size,
    )

@OptIn(ExperimentalApi::class)
private fun SpanContext.toSerializable(): io.opentelemetry.kotlin.framework.serialization.SerializableSpanContext =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableSpanContext(
        traceId,
        spanId,
        traceFlags.hex,
        traceState.asMap()
    )
