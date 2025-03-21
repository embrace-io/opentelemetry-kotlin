package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.StatusCode

internal fun StatusCode.bind(): io.opentelemetry.api.trace.StatusCode = when (this) {
    StatusCode.Ok -> io.opentelemetry.api.trace.StatusCode.OK
    StatusCode.Unset -> io.opentelemetry.api.trace.StatusCode.UNSET
    is StatusCode.Error -> io.opentelemetry.api.trace.StatusCode.ERROR
}
