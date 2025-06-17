package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData

internal fun OtelJavaStatusData.toSerializable() = SerializableSpanStatusData(
    code = statusCode.ordinal,
    description = description,
)
