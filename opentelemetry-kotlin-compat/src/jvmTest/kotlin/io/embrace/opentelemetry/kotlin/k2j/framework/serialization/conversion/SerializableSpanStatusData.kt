package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanStatusData

internal fun OtelJavaStatusData.toSerializable() = SerializableSpanStatusData(
    code = statusCode.ordinal,
    description = description,
)
