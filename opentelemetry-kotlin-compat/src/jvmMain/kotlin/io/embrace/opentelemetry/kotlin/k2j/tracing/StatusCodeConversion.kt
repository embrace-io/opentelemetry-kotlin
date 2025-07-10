package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

public fun StatusCode.convertToOtelJava(): OtelJavaStatusCode = when (this) {
    StatusCode.Unset -> OtelJavaStatusCode.UNSET
    StatusCode.Ok -> OtelJavaStatusCode.OK
    is StatusCode.Error -> OtelJavaStatusCode.ERROR
}

public fun OtelJavaStatusCode.convertToOtelKotlin(): StatusCode = when (this) {
    OtelJavaStatusCode.UNSET -> StatusCode.Unset
    OtelJavaStatusCode.OK -> StatusCode.Ok
    OtelJavaStatusCode.ERROR -> StatusCode.Error(null)
}
