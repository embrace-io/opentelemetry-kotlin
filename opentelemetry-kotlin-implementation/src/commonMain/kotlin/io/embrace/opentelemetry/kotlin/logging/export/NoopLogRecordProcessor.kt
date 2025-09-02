package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

/**
 * A no-op implementation of [LogRecordProcessor]. This processor does nothing with the log records
 * and always returns success for lifecycle operations.
 */
@OptIn(ExperimentalApi::class)
public class NoopLogRecordProcessor private constructor() : LogRecordProcessor {

    override fun onEmit(log: ReadWriteLogRecord, context: Context) {
        // No-op
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success

    override fun shutdown(): OperationResultCode = OperationResultCode.Success

    public companion object {
        /**
         * The singleton instance of [NoopLogRecordProcessor].
         */
        public val INSTANCE: NoopLogRecordProcessor = NoopLogRecordProcessor()
    }
}