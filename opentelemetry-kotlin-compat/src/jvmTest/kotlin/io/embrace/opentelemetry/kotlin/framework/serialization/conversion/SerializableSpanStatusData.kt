package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData

internal fun OtelJavaStatusData.toSerializable() = SerializableSpanStatusData(
    name = statusCode.name,
    description = description,
)
