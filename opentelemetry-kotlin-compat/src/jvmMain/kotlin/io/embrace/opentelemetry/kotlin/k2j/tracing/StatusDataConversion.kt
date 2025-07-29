package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
public fun StatusData.toOtelJava(): OtelJavaStatusData =
    if (description.isEmpty()) {
        when (statusCode) {
            StatusCode.Unset -> OtelJavaStatusData.unset()
            StatusCode.Ok -> OtelJavaStatusData.ok()
            StatusCode.Error -> OtelJavaStatusData.error()
        }
    } else {
        OtelJavaStatusData.create(statusCode.toOtelJava(), description)
    }
