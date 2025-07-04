package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeClock
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableReadableLogRecord
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableReadableSpan
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.kotlinApi
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalApi::class)
internal class OtelKotlinHarness {

    private companion object {
        private const val EXPORT_POLL_ATTEMPTS = 1000
        private const val EXPORT_POLL_WAIT_US = 10L
    }

    private val spanExporter = InMemorySpanExporter()
    private val logRecordExporter = InMemoryLogRecordExporter()
    private val fakeClock = FakeClock()

    val kotlinApi = OpenTelemetryInstance.kotlinApi( // TODO: test resource
        tracerProvider = {
            addSpanProcessor(InMemorySpanProcessor(spanExporter))
        },
        loggerProvider = {
            addLogRecordProcessor(InMemoryLogRecordProcessor(logRecordExporter))
        },
        clock = fakeClock
    )

    internal fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null,
        sanitizeSpanContextIds: Boolean = true,
        assertions: (spans: List<SerializableReadableSpan>) -> Unit = {},
    ) {
        val observedSpans: List<ReadableSpan> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { spanExporter.exportedSpans }
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
        assertions: (logs: List<SerializableReadableLogRecord>) -> Unit = {},
    ) {
        val observedLogRecords: List<ReadableLogRecord> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { logRecordExporter.exportedLogRecords }
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
