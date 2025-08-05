package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class SpanLimitsConfigImpl : SpanLimitsConfigDsl {
    override var attributeCountLimit: Int = 1000
    override var linkCountLimit: Int = 1000
    override var eventCountLimit: Int = 1000
    override var attributeCountPerEventLimit: Int = 1000
    override var attributeCountPerLinkLimit: Int = 1000
}
