package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.opentelemetry.api.trace.StatusCode

internal fun StatusCode.convertToOtelKotlin(description: String?): io.embrace.opentelemetry.kotlin.StatusCode = when (this) {
    StatusCode.UNSET -> io.embrace.opentelemetry.kotlin.StatusCode.Unset
    StatusCode.OK -> io.embrace.opentelemetry.kotlin.StatusCode.Ok
    StatusCode.ERROR -> io.embrace.opentelemetry.kotlin.StatusCode.Error(description)
}
