package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaLogRecordExporter
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadWriteLogRecord
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class LogRecordExporterExtTest {

    @Test
    fun toOtelKotlinLogRecordExporter() {
        val impl = FakeOtelJavaLogRecordExporter()
        val adapter = impl.toOtelKotlinLogRecordExporter()
        val timestamp = 500L
        adapter.export(mutableListOf(FakeReadWriteLogRecord(timestamp = timestamp)))

        val export = impl.exports.single()
        assertEquals(timestamp, export.timestampEpochNanos)
    }
}
