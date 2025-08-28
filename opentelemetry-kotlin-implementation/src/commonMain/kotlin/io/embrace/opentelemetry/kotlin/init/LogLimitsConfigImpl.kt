package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.DEFAULT_ATTRIBUTE_LIMIT

@OptIn(ExperimentalApi::class)
internal class LogLimitsConfigImpl : LogLimitsConfigDsl {
    override var attributeCountLimit: Int = DEFAULT_ATTRIBUTE_LIMIT
    override var attributeValueLengthLimit: Int = Int.MAX_VALUE
}
