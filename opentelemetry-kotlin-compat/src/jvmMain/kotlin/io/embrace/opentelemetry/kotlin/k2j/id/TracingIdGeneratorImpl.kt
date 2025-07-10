package io.embrace.opentelemetry.kotlin.k2j.id

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanId
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceId

public class TracingIdGeneratorImpl(
    private val generator: OtelJavaIdGenerator
) : TracingIdGenerator {
    override fun generateSpanId(): String = generator.generateSpanId()
    override fun generateTraceId(): String = generator.generateTraceId()
    override val invalidTraceId: String = OtelJavaTraceId.getInvalid()
    override val invalidSpanId: String = OtelJavaSpanId.getInvalid()
}
