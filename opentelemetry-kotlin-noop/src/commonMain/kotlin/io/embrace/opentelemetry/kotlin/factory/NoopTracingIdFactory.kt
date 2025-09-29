package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopTracingIdFactory : TracingIdFactory {
    private val empty = ByteArray(0)
    override fun generateSpanIdBytes(): ByteArray = empty
    override fun generateTraceIdBytes(): ByteArray = empty
    override val invalidTraceId: ByteArray = empty
    override val invalidSpanId: ByteArray = empty
}
