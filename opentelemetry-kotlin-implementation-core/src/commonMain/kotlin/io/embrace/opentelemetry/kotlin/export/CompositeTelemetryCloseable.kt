package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler

@OptIn(ExperimentalApi::class)
public class CompositeTelemetryCloseable(
    private val closeables: List<TelemetryCloseable>,
    private val sdkErrorHandler: SdkErrorHandler,
) : TelemetryCloseable {

    override fun forceFlush(): OperationResultCode =
        batchExportOperation(closeables, sdkErrorHandler, TelemetryCloseable::forceFlush)

    override fun shutdown(): OperationResultCode =
        batchExportOperation(closeables, sdkErrorHandler, TelemetryCloseable::shutdown)
}
