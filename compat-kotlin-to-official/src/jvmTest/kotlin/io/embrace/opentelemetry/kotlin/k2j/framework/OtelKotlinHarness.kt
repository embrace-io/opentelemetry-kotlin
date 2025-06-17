package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkLoggerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableLogRecordData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.toSerializable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal class OtelKotlinHarness {

    private companion object {
        private const val EXPORT_POLL_ATTEMPTS = 1000
        private const val EXPORT_POLL_WAIT_US = 10L
    }

    private val spanExporter = InMemorySpanExporter()
    private val logRecordExporter = InMemoryLogRecordExporter()
    private val fakeClock = FakeClock()

    private val tracerProvider: OtelJavaSdkTracerProvider = OtelJavaSdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(spanExporter))
        .setClock(fakeClock)
        .build()

    private val loggerProvider: OtelJavaSdkLoggerProvider = OtelJavaSdkLoggerProvider.builder()
        .addLogRecordProcessor(InMemoryLogRecordProcessor(logRecordExporter))
        .setClock(fakeClock)
        .build()

    val sdk: OtelJavaOpenTelemetry = OtelJavaOpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .setLoggerProvider(loggerProvider)
        .build()

    val clock: ClockAdapter = ClockAdapter(fakeClock)

    internal fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null,
        sanitizeSpanContextIds: Boolean = true,
        assertions: (spans: List<SerializableSpanData>) -> Unit = {},
    ) {
        val observedSpans: List<OtelJavaSpanData> = awaitExportedData(
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
        assertions: (logs: List<SerializableLogRecordData>) -> Unit = {},
    ) {
        val observedLogRecords: List<OtelJavaLogRecordData> = awaitExportedData(
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
