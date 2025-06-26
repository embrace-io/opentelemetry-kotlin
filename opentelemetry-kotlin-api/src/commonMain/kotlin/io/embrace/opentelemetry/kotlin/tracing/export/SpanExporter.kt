package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable

/**
 * An interface for exporting spans to an arbitrary destination.
 */
@ExperimentalApi
public interface SpanExporter : TelemetryCloseable {

    /**
     * Exports a batch of spans. This operation is considered successful if the implementation
     * returns [OperationResultCode.Success]. If the export operation fails the batch must be dropped.
     */
    public fun export(telemetry: List<SpanData>): OperationResultCode
}
