package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.init.config.LogLimitConfig

internal val fakeLogLimitsConfig = LogLimitConfig(
    attributeCountLimit = 100,
    attributeValueLengthLimit = 100,
)
