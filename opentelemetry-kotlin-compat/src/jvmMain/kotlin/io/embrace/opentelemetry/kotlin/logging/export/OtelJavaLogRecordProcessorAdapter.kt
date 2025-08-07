package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordProcessor
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.context.toOtelKotlinContext

@OptIn(ExperimentalApi::class)
internal class OtelJavaLogRecordProcessorAdapter(
    private val impl: LogRecordProcessor
) : OtelJavaLogRecordProcessor {

    override fun onEmit(
        context: OtelJavaContext,
        logRecord: OtelJavaReadWriteLogRecord
    ) {
        impl.onEmit(ReadWriteLogRecordAdapter(logRecord), context.toOtelKotlinContext())
    }
}
