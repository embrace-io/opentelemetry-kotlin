package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.attributes.attrsFromMap
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData

@OptIn(ExperimentalApi::class)
public fun LinkData.toOtelJavaLinkData(): OtelJavaLinkData = OtelJavaLinkData.create(
    spanContext.toOtelJavaSpanContext(),
    attrsFromMap(attributes)
)
