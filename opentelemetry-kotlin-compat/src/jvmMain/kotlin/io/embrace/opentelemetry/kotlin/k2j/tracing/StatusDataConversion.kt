package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
public fun StatusData.toOtelJava(): OtelJavaStatusData = when (this) {
    StatusData.Unset -> OtelJavaStatusData.unset()
    StatusData.Ok -> OtelJavaStatusData.ok()
    is StatusData.Error -> OtelJavaStatusData.create(OtelJavaStatusCode.ERROR, description)
}

@OptIn(ExperimentalApi::class)
public fun OtelJavaStatusData.toOtelKotlin(): StatusData = when (statusCode) {
    OtelJavaStatusCode.UNSET -> StatusData.Unset
    OtelJavaStatusCode.OK -> StatusData.Ok
    OtelJavaStatusCode.ERROR -> StatusData.Error(description)
    null -> StatusData.Unset
}
