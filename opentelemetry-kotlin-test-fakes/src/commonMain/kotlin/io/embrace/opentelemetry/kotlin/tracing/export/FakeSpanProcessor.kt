package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
class FakeSpanProcessor(
    var startRequired: Boolean = true,
    var endRequired: Boolean = true,
    var flushCode: OperationResultCode = OperationResultCode.Success,
    var shutdownCode: OperationResultCode = OperationResultCode.Success,
    var startAction: (ReadWriteSpan, Context) -> Unit = { _, _ -> },
    var endAction: (ReadableSpan) -> Unit = {}
) : SpanProcessor {

    override fun onStart(
        span: ReadWriteSpan,
        parentContext: Context
    ) = startAction(span, parentContext)

    override fun onEnd(span: ReadableSpan) = endAction(span)
    override fun isStartRequired(): Boolean = startRequired
    override fun isEndRequired(): Boolean = endRequired
    override fun forceFlush(): OperationResultCode = flushCode
    override fun shutdown(): OperationResultCode = shutdownCode
}
