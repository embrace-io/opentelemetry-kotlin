package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.StatusData

internal fun StatusData.toSerializable() = SerializableSpanStatusData(
    code = statusCode.ordinal,
    description = description,
)
