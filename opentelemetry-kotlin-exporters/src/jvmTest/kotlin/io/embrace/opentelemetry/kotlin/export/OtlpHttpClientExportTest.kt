package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.createOtlpHttpLogRecordExporter
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext
import io.embrace.opentelemetry.kotlin.tracing.data.FakeLinkData
import io.embrace.opentelemetry.kotlin.tracing.data.FakeSpanData
import io.embrace.opentelemetry.kotlin.tracing.export.createOtlpHttpSpanExporter
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class OtlpHttpClientExportTest {

    private val baseUrl = "http://127.0.0.1:4318"

    private val spanContext = FakeSpanContext(
        traceId = "1".repeat(16),
        spanId = "2".repeat(8),
    )

    @Test
    fun testLogExport() {
        val exporter = createOtlpHttpLogRecordExporter(baseUrl)
        val record = FakeReadableLogRecord(spanContext = spanContext)
        exporter.export(listOf(record))

        // avoid test terminating before server receives response
        Thread.sleep(1000)
    }

    @Test
    fun testTraceExport() {
        val exporter = createOtlpHttpSpanExporter(baseUrl)
        val links = listOf(
            FakeLinkData(
                spanContext = FakeSpanContext(
                    traceId = "3".repeat(16),
                    spanId = "4".repeat(8),
                )
            )
        )
        val spanData = FakeSpanData(spanContext = spanContext, links = links)
        exporter.export(listOf(spanData))

        // avoid test terminating before server receives response
        Thread.sleep(1000)
    }
}