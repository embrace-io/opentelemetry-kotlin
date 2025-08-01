package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanId
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceId

@OptIn(ExperimentalApi::class)
internal class TracingIdCreatorImpl(
    private val generator: OtelJavaIdGenerator = OtelJavaIdGenerator.random()
) : TracingIdCreator {
    override fun generateSpanId(): String = generator.generateSpanId()
    override fun generateTraceId(): String = generator.generateTraceId()
    override val invalidTraceId: String = OtelJavaTraceId.getInvalid()
    override val invalidSpanId: String = OtelJavaSpanId.getInvalid()
}
