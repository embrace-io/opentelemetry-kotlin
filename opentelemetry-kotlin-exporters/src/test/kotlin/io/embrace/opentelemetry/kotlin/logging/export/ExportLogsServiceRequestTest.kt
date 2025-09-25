package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import kotlin.test.assertEquals
import org.junit.Test

@OptIn(ExperimentalApi::class)
class ExportLogsServiceRequestTest {

    @Test
    fun testCreateExportLogsServiceRequest() {
        val record = FakeReadableLogRecord()
        val request = listOf(record).toExportLogsServiceRequest()
        assertEquals(1, request.resourceLogsCount)
        val resourceLogs = request.resourceLogsList[0]

        assertEquals(1, resourceLogs.scopeLogsCount)
        val scopeLogs = resourceLogs.scopeLogsList[0]

        assertEquals(1, scopeLogs.logRecordsCount)
        val logRecords = scopeLogs.logRecordsList[0]

        assertEquals(record.body, logRecords.body.stringValue)
    }
}
