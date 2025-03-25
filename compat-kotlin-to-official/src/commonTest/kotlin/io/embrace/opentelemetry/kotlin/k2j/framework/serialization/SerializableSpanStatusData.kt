package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.StatusData
import kotlinx.serialization.Serializable

internal fun StatusData.toSerializable() = SerializableSpanStatusData(
    code = statusCode.ordinal,
    description = description,
)

@Serializable
internal data class SerializableSpanStatusData(
    val code: Int,
    val description: String,
)
