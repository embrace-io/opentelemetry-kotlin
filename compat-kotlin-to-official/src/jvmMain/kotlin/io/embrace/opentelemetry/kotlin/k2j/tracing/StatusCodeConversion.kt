package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.StatusCode

internal fun StatusCode.convertToOtelJava(): io.opentelemetry.api.trace.StatusCode = when (this) {
    StatusCode.Unset -> io.opentelemetry.api.trace.StatusCode.UNSET
    StatusCode.Ok -> io.opentelemetry.api.trace.StatusCode.OK
    is StatusCode.Error -> io.opentelemetry.api.trace.StatusCode.ERROR
}
