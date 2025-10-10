package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordProcessor
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.toOtelJavaContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.toOperationResultCode

@OptIn(ExperimentalApi::class)
internal class LogRecordProcessorAdapter(
    private val impl: OtelJavaLogRecordProcessor
) : LogRecordProcessor {

    override fun onEmit(
        log: ReadWriteLogRecord,
        context: Context
    ) {
        if (log is ReadWriteLogRecordAdapter) {
            impl.onEmit(context.toOtelJavaContext(), log.impl)
        }
    }

    override fun shutdown(): OperationResultCode = impl.shutdown().toOperationResultCode()
    override fun forceFlush(): OperationResultCode = impl.forceFlush().toOperationResultCode()
}
