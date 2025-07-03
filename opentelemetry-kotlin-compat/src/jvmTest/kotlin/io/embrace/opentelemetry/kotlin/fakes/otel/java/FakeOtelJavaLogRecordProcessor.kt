package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordProcessor
import io.opentelemetry.context.Context
import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.logs.ReadWriteLogRecord

internal class FakeOtelJavaLogRecordProcessor : OtelJavaLogRecordProcessor {

    var flushCount = 0
    var shutdownCount = 0
    val exports: MutableList<ReadWriteLogRecord> = mutableListOf()

    override fun onEmit(context: Context, logRecord: ReadWriteLogRecord) {
        exports.add(logRecord)
    }

    override fun forceFlush(): CompletableResultCode {
        flushCount += 1
        return CompletableResultCode.ofSuccess()
    }

    override fun shutdown(): CompletableResultCode {
        shutdownCount += 1
        return CompletableResultCode.ofSuccess()
    }
}
