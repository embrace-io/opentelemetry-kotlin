package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorSeverity
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

/**
 * A [SpanProcessor] that immediately exports each span when it ends.
 * This processor is suitable for scenarios where low latency is more important than throughput.
 *
 * @param spanExporter The [SpanExporter] to export spans to
 * @param sdkErrorHandler Handler for SDK errors
 */
@OptIn(ExperimentalApi::class)
public class SimpleSpanProcessor(
    private val spanExporter: SpanExporter,
    private val sdkErrorHandler: SdkErrorHandler
) : SpanProcessor {

    override fun onStart(span: ReadWriteSpan, parentContext: Context) {
        // No processing needed on start for simple processor
    }

    override fun onEnd(span: ReadableSpan) {
        try {
            val result = spanExporter.export(listOf(span.toSpanData()))
            if (result != OperationResultCode.Success) {
                sdkErrorHandler.onUserCodeError(
                    RuntimeException("Span export failed with result: $result"),
                    "Failed to export span",
                    SdkErrorSeverity.WARNING
                )
            }
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during span export",
                SdkErrorSeverity.WARNING
            )
        }
    }

    override fun isStartRequired(): Boolean = false
    override fun isEndRequired(): Boolean = true

    override fun forceFlush(): OperationResultCode {
        return try {
            spanExporter.forceFlush()
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during force flush",
                SdkErrorSeverity.WARNING
            )
            OperationResultCode.Failure
        }
    }

    override fun shutdown(): OperationResultCode {
        return try {
            spanExporter.shutdown()
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during shutdown",
                SdkErrorSeverity.WARNING
            )
            OperationResultCode.Failure
        }
    }
}