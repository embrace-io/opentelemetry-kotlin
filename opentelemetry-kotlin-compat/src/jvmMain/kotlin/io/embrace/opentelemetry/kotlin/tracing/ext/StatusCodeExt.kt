package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

@OptIn(ExperimentalApi::class)
internal fun StatusCode.toOtelJavaStatusCode(): OtelJavaStatusCode = when (this) {
    StatusCode.Unset -> OtelJavaStatusCode.UNSET
    StatusCode.Ok -> OtelJavaStatusCode.OK
    StatusCode.Error -> OtelJavaStatusCode.ERROR
}
