package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogLimits

@ExperimentalApi
internal class LogLimitsConfigImpl : LogLimitsConfigDsl {

    private val builder = OtelJavaLogLimits.builder()

    override var attributeCountLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxNumberOfAttributes(value)
        }

    override var attributeValueLengthLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxAttributeValueLength(value)
        }

    fun build(): OtelJavaLogLimits = builder.build()
}
