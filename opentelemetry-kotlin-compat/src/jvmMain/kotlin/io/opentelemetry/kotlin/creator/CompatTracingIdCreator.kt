package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.opentelemetry.kotlin.aliases.OtelJavaSpanId
import io.opentelemetry.kotlin.aliases.OtelJavaTraceId

@OptIn(ExperimentalApi::class)
internal class CompatTracingIdCreator(
    private val generator: OtelJavaIdGenerator = OtelJavaIdGenerator.random()
) : TracingIdCreator {
    override fun generateSpanId(): String = generator.generateSpanId()
    override fun generateTraceId(): String = generator.generateTraceId()
    override val invalidTraceId: String = OtelJavaTraceId.getInvalid()
    override val invalidSpanId: String = OtelJavaSpanId.getInvalid()
}
