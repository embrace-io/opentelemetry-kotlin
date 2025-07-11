package io.embrace.opentelemetry.kotlin.k2j.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanLimits
import io.embrace.opentelemetry.kotlin.init.SpanLimitsConfigDsl

@ExperimentalApi
internal class SpanLimitsConfigImpl : SpanLimitsConfigDsl {

    private val builder = OtelJavaSpanLimits.builder()

    override var attributeCountLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxNumberOfAttributes(value)
        }

    override var linkCountLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxNumberOfLinks(value)
        }

    override var eventCountLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxNumberOfEvents(value)
        }

    override var attributeCountPerEventLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxNumberOfAttributesPerEvent(value)
        }

    override var attributeCountPerLinkLimit: Int = 0
        set(value) {
            field = value
            builder.setMaxNumberOfAttributesPerLink(value)
        }

    fun build(): OtelJavaSpanLimits = builder.build()
}
