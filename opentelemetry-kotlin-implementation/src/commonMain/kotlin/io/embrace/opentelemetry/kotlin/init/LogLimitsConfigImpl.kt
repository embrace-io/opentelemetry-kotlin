package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class LogLimitsConfigImpl : LogLimitsConfigDsl {
    override var attributeCountLimit: Int = 1000
    override var attributeValueLengthLimit: Int = 1000
}
