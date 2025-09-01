package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class FakeTracingIdFactory : TracingIdFactory {
    override fun generateSpanId(): String = "1234561234561234"
    override fun generateTraceId(): String = "12345612345612341234561234561234"
    override val invalidTraceId: String = "0000000000000000"
    override val invalidSpanId: String = "00000000000000000000000000000000"
}
