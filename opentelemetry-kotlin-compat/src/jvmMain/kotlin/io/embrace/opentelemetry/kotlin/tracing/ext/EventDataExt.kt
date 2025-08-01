package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.attributes.attrsFromMap
import io.embrace.opentelemetry.kotlin.tracing.data.EventData

@OptIn(ExperimentalApi::class)
public fun EventData.toOtelJavaEventData(): OtelJavaEventData = OtelJavaEventData.create(
    timestamp,
    name,
    attrsFromMap(attributes)
)
