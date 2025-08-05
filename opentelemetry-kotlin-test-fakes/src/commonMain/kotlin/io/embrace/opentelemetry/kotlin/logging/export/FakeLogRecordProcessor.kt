package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

@OptIn(ExperimentalApi::class)
class FakeLogRecordProcessor(
    var flushCode: OperationResultCode = OperationResultCode.Success,
    var shutdownCode: OperationResultCode = OperationResultCode.Success,
    var action: (log: ReadWriteLogRecord, context: Context) -> Unit = { _, _ -> }
) : LogRecordProcessor {

    override fun onEmit(
        log: ReadWriteLogRecord,
        context: Context
    ) = action(log, context)

    override fun forceFlush(): OperationResultCode = flushCode
    override fun shutdown(): OperationResultCode = shutdownCode
}
