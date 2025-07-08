package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

public fun StatusCode.convertToOtelJava(): OtelJavaStatusCode = when (this) {
    StatusCode.Unset -> OtelJavaStatusCode.UNSET
    StatusCode.Ok -> OtelJavaStatusCode.OK
    is StatusCode.Error -> OtelJavaStatusCode.ERROR
}
