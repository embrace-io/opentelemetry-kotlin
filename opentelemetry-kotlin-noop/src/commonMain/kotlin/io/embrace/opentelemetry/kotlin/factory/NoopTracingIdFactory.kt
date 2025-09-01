package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopTracingIdFactory : TracingIdFactory {
    override fun generateSpanId(): String = ""
    override fun generateTraceId(): String = ""
    override val invalidTraceId: String = ""
    override val invalidSpanId: String = ""
}
