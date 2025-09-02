package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

/**
 * A no-op implementation of [SpanProcessor] that does nothing when spans are started or ended.
 * This processor is optimized for minimal overhead and returns false for both
 * [isStartRequired] and [isEndRequired] to avoid unnecessary calls.
 */
@OptIn(ExperimentalApi::class)
public object NoopSpanProcessor : SpanProcessor {

    override fun onStart(span: ReadWriteSpan, parentContext: Context) {
        // No-op
    }

    override fun onEnd(span: ReadableSpan) {
        // No-op
    }

    override fun isStartRequired(): Boolean = false
    override fun isEndRequired(): Boolean = false
    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}