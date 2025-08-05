package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class SpanLimitsConfigImpl : SpanLimitsConfigDsl {
    override var attributeCountLimit: Int = 128
    override var linkCountLimit: Int = 128
    override var eventCountLimit: Int = 128
    override var attributeCountPerEventLimit: Int = 128
    override var attributeCountPerLinkLimit: Int = 128
}
