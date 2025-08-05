package io.embrace.opentelemetry.kotlin.testing.common

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
class TestSpanProcessor(
    private val runOnStart: (readWriteSpan: ReadWriteSpan) -> Unit = {},
    private val runOnEnd: (readableSpan: ReadableSpan) -> Unit = {},
) : SpanProcessor {
    override fun onStart(span: ReadWriteSpan, parentContext: Context) = runOnStart(span)

    override fun onEnd(span: ReadableSpan) = runOnEnd(span)

    override fun isStartRequired(): Boolean = true

    override fun isEndRequired(): Boolean = true

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success

    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
