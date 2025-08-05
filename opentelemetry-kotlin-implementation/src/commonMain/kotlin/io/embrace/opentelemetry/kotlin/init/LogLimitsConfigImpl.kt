package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class LogLimitsConfigImpl : LogLimitsConfigDsl {
    override var attributeCountLimit: Int = 128
    override var attributeValueLengthLimit: Int = Int.MAX_VALUE
}
