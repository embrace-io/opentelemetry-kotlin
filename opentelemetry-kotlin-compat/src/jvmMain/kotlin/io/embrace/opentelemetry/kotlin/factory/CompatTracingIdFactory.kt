package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanId
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceId

@OptIn(ExperimentalApi::class)
internal class CompatTracingIdFactory(
    private val generator: OtelJavaIdGenerator = OtelJavaIdGenerator.random()
) : TracingIdFactory {
    override fun generateSpanIdBytes(): ByteArray = generator.generateSpanId().hexToByteArray()
    override fun generateTraceIdBytes(): ByteArray = generator.generateTraceId().hexToByteArray()
    override val invalidTraceId: ByteArray = OtelJavaTraceId.getInvalid().hexToByteArray()
    override val invalidSpanId: ByteArray = OtelJavaSpanId.getInvalid().hexToByteArray()
}
