package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

/**
 * An interface for exporting logs to an arbitrary destination.
 */
@ExperimentalApi
public interface LogRecordExporter : TelemetryCloseable {

    /**
     * Exports a batch of logs. This operation is considered successful if the implementation
     * returns [OperationResultCode.Success]. If the export operation fails the batch must be dropped.
     */
    public fun export(telemetry: List<ReadableLogRecord>): OperationResultCode
}
