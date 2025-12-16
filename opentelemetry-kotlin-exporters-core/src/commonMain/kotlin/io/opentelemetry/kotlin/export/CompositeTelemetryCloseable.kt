package io.opentelemetry.kotlin.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.error.SdkErrorHandler

@OptIn(ExperimentalApi::class)
internal class CompositeTelemetryCloseable(
    private val closeables: List<TelemetryCloseable>,
    private val sdkErrorHandler: SdkErrorHandler,
) : TelemetryCloseable {

    override fun forceFlush(): OperationResultCode =
        _root_ide_package_.io.opentelemetry.kotlin.export.batchExportOperation(
            closeables,
            sdkErrorHandler,
            TelemetryCloseable::forceFlush
        )

    override fun shutdown(): OperationResultCode =
        _root_ide_package_.io.opentelemetry.kotlin.export.batchExportOperation(
            closeables,
            sdkErrorHandler,
            TelemetryCloseable::shutdown
        )
}
