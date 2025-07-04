package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableStatusCode
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

internal fun StatusCode.toSerializable() = SerializableStatusCode(
    description = when (this) {
        StatusCode.Ok -> "OK"
        is StatusCode.Error -> "ERROR"
        StatusCode.Unset -> "UNSET"
    }
)
