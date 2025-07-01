package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

internal fun OtelJavaStatusCode.convertToOtelKotlin(description: String?): StatusCode = when (this) {
    OtelJavaStatusCode.UNSET -> StatusCode.Unset
    OtelJavaStatusCode.OK -> StatusCode.Ok
    OtelJavaStatusCode.ERROR -> StatusCode.Error(description)
}
