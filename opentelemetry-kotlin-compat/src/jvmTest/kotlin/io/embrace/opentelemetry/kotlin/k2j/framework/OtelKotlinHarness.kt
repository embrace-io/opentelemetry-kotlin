package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.compatWithOtelKotlin
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeClock
import io.embrace.opentelemetry.kotlin.j2k.logging.export.toLogRecordData
import io.embrace.opentelemetry.kotlin.j2k.tracing.export.toSpanData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableLogRecordData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.kotlinApi
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalApi::class)
internal class OtelKotlinHarness(
    private val resourceConfig: TestResourceConfig = TestResourceConfig(),
) {

    private companion object {
        private const val EXPORT_POLL_ATTEMPTS = 1000
        private const val EXPORT_POLL_WAIT_US = 10L
    }

    private val spanExporter = InMemorySpanExporter()
    private val logRecordExporter = InMemoryLogRecordExporter()

    val kotlinApi = OpenTelemetryInstance.kotlinApi(
        clock = FakeClock(),
        tracerProvider = {
            resourceConfig.attributes?.let { resource(it, resourceConfig.schemaUrl) }
            addSpanProcessor(InMemorySpanProcessor(spanExporter))
        },
        loggerProvider = {
            resourceConfig.attributes?.let { resource(it, resourceConfig.schemaUrl) }
            addLogRecordProcessor(InMemoryLogRecordProcessor(logRecordExporter))
        }
    )

    val javaApi = OpenTelemetryInstance.compatWithOtelKotlin(kotlinApi)

    internal fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null,
        sanitizeSpanContextIds: Boolean = true,
        assertions: (spans: List<SerializableSpanData>) -> Unit = {},
    ) {
        val observedSpans: List<OtelJavaSpanData> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { spanExporter.exportedSpans.map(ReadableSpan::toSpanData) }
        )
        val data = observedSpans.map { it.toSerializable(sanitizeSpanContextIds) }
        assertions(data)

        if (goldenFileName != null) {
            compareGoldenFile(data, goldenFileName)
        }
    }

    internal fun assertLogRecords(
        expectedCount: Int,
        goldenFileName: String? = null,
        sanitizeSpanContextIds: Boolean = true,
        assertions: (logs: List<SerializableLogRecordData>) -> Unit = {},
    ) {
        val observedLogRecords: List<OtelJavaLogRecordData> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { logRecordExporter.exportedLogRecords.map(ReadableLogRecord::toLogRecordData) }
        )
        val data = observedLogRecords.map { it.toSerializable(sanitizeSpanContextIds) }
        assertions(data)

        if (goldenFileName != null) {
            compareGoldenFile(data, goldenFileName)
        }
    }

    private fun <T> awaitExportedData(
        expectedCount: Int,
        supplier: () -> List<T>
    ): List<T> {
        val countDownLatch = CountDownLatch(1)
        repeat(EXPORT_POLL_ATTEMPTS) {
            if (supplier().size != expectedCount) {
                countDownLatch.await(EXPORT_POLL_WAIT_US, TimeUnit.MICROSECONDS)
            } else {
                return supplier()
            }
        }
        val data = supplier()
        throw TimeoutException(
            "Timeout. Expected $expectedCount elements, but got ${data.size}. " +
                "Found: ${data.joinToString { it.toString() }}"
        )
    }
}
