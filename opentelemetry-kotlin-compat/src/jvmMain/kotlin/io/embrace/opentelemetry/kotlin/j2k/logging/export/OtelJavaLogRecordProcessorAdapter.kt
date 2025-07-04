package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordProcessor
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.j2k.bridge.toOtelKotlin
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.opentelemetry.context.Context

@OptIn(ExperimentalApi::class)
public class OtelJavaLogRecordProcessorAdapter(
    private val impl: LogRecordProcessor
) : OtelJavaLogRecordProcessor {

    override fun onEmit(
        context: Context,
        logRecord: OtelJavaReadWriteLogRecord
    ) {
        impl.onEmit(logRecord.toOtelKotlin(), context.toOtelKotlin())
    }
}
