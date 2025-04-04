package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaStatusCode

internal fun StatusCode.convertToOtelJava(): OtelJavaStatusCode = when (this) {
    StatusCode.Unset -> OtelJavaStatusCode.UNSET
    StatusCode.Ok -> OtelJavaStatusCode.OK
    is StatusCode.Error -> OtelJavaStatusCode.ERROR
}
