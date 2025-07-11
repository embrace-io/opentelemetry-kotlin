package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

@OptIn(ExperimentalApi::class)
internal fun OtelJavaStatusData.convertToOtelKotlin(): StatusCode = when (statusCode) {
    OtelJavaStatusCode.UNSET -> StatusCode.Unset
    OtelJavaStatusCode.OK -> StatusCode.Ok
    OtelJavaStatusCode.ERROR -> StatusCode.Error(description)
    null -> StatusCode.Unset
}
