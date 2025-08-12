package io.embrace.opentelemetry.kotlin.integration.test.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableResource
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanContext
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
internal fun ReadableSpan.toSerializable(): SerializableSpanData = SerializableSpanData(
    name = "",
    kind = "",
    statusData = SerializableSpanStatusData("", ""),
    spanContext = SerializableSpanContext("", "", "", emptyMap()),
    parentSpanContext = SerializableSpanContext("", "", "", emptyMap()),
    startTimestamp = -1,
    attributes = attributes.mapValues { it.value.toString() },
    events = emptyList(),
    links = emptyList(),
    endTimestamp = -1,
    ended = false,
    totalRecordedEvents = -1,
    totalRecordedLinks = -1,
    totalAttributeCount = -1,
    resource = SerializableResource("", emptyMap()),
    instrumentationScopeInfo = SerializableInstrumentationScopeInfo("", "", "", emptyMap())
)
