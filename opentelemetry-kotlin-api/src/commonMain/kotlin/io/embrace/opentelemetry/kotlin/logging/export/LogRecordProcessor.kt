package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

/**
 * Processes logs before they are exported as batches.
 */
@ExperimentalApi
public interface LogRecordProcessor : TelemetryCloseable {

    /**
     * Invoked when a log record is emitted.
     *
     * @param log The log record that has been emitted.
     * @param context The context associated with the log record.
     */
    public fun onEmit(log: ReadWriteLogRecord, context: Context)
}
