package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
internal fun StatusData.toOtelJavaStatusData(): OtelJavaStatusData = when (this) {
    StatusData.Unset -> OtelJavaStatusData.unset()
    StatusData.Ok -> OtelJavaStatusData.ok()
    is StatusData.Error -> OtelJavaStatusData.create(OtelJavaStatusCode.ERROR, description)
}
