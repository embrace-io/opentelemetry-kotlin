package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class FakeTracingIdCreator : TracingIdCreator {
    override fun generateSpanId(): String = "0000000000000000"
    override fun generateTraceId(): String = "00000000000000000000000000000000"
    override val invalidTraceId: String = "0000000000000000"
    override val invalidSpanId: String = "00000000000000000000000000000000"
}
