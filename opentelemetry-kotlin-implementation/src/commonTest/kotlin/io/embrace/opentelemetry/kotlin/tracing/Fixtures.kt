package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.init.config.LogLimitConfig
import io.embrace.opentelemetry.kotlin.init.config.SpanLimitConfig

internal val fakeSpanLimitsConfig = SpanLimitConfig(
    attributeCountLimit = 100,
    linkCountLimit = 100,
    eventCountLimit = 100,
    attributeCountPerEventLimit = 100,
    attributeCountPerLinkLimit = 100
)

internal val fakeLogLimitsConfig = LogLimitConfig(
    attributeCountLimit = 100,
    attributeValueLengthLimit = 100,
)
