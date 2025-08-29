package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopTracingIdCreator : TracingIdCreator {
    override fun generateSpanId(): String = ""
    override fun generateTraceId(): String = ""
    override val invalidTraceId: String = ""
    override val invalidSpanId: String = ""
}
