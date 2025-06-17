package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode

internal fun OtelJavaStatusCode.convertToOtelKotlin(description: String?): StatusCode = when (this) {
    OtelJavaStatusCode.UNSET -> StatusCode.Unset
    OtelJavaStatusCode.OK -> StatusCode.Ok
    OtelJavaStatusCode.ERROR -> StatusCode.Error(description)
}
