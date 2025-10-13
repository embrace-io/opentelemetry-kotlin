package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadWriteLogRecord
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SimpleLogRecordProcessorTest {

    @Test
    fun testSpanProcessorDefaults() {
        val processor = SimpleLogRecordProcessor(FakeLogRecordExporter())
        assertEquals(OperationResultCode.Success, processor.shutdown())
        assertEquals(OperationResultCode.Success, processor.forceFlush())
    }

    @Test
    fun testSpanProcessorExport() {
        val exporter = FakeLogRecordExporter()
        val processor = SimpleLogRecordProcessor(exporter)

        val log = FakeReadWriteLogRecord(body = "my_log")
        processor.onEmit(log, FakeContext())

        val export = exporter.logs.single()
        assertEquals(log.body, export.body)
    }
}
