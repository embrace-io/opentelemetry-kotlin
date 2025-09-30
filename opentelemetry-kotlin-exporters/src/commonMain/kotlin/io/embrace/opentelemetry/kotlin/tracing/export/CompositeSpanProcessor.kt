package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.CompositeTelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.batchExportOperation
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
public class CompositeSpanProcessor(
    private val processors: List<SpanProcessor>,
    private val sdkErrorHandler: SdkErrorHandler,
    private val telemetryCloseable: TelemetryCloseable = CompositeTelemetryCloseable(
        processors,
        sdkErrorHandler
    ),
) : SpanProcessor, TelemetryCloseable by telemetryCloseable {

    override fun onStart(
        span: ReadWriteSpan,
        parentContext: Context
    ) {
        batchExportOperation(processors, sdkErrorHandler) {
            if (it.isStartRequired()) {
                it.onStart(span, parentContext)
            }
            OperationResultCode.Success
        }
    }

    override fun onEnd(span: ReadableSpan) {
        batchExportOperation(processors, sdkErrorHandler) {
            if (it.isEndRequired()) {
                it.onEnd(span)
            }
            OperationResultCode.Success
        }
    }

    override fun isStartRequired(): Boolean = true
    override fun isEndRequired(): Boolean = true
}
