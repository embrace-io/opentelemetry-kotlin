package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.createOpenTelemetryKotlin
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.decorateKotlinApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableLogRecordData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanData
import io.embrace.opentelemetry.kotlin.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.logging.export.toLogRecordData
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalApi::class)
internal class OtelKotlinHarness {

    val config: TestHarnessConfig = TestHarnessConfig()

    private companion object {
        private const val EXPORT_POLL_ATTEMPTS = 1000
        private const val EXPORT_POLL_WAIT_US = 10L
    }

    private val spanExporter = InMemorySpanExporter()
    private val logRecordExporter = InMemoryLogRecordExporter()

    val kotlinApi: OpenTelemetry by lazy {
        OpenTelemetryInstance.createOpenTelemetryKotlin(
            clock = FakeClock(),
            tracerProvider = {
                config.attributes?.let { resource(it, config.schemaUrl) }
                addSpanProcessor(InMemorySpanProcessor(spanExporter))
                config.spanProcessors.forEach { addSpanProcessor(it) }
                spanLimits(config.spanLimits)
            },
            loggerProvider = {
                config.attributes?.let { resource(it, config.schemaUrl) }
                addLogRecordProcessor(InMemoryLogRecordProcessor(logRecordExporter))
                config.logRecordProcessors.forEach { addLogRecordProcessor(it) }
                logLimits(config.logLimits)
            }
        )
    }

    val javaApi by lazy {
        OpenTelemetryInstance.decorateKotlinApi(kotlinApi)
    }

    val tracerProvider: TracerProvider
        get() = kotlinApi.tracerProvider

    val tracer: Tracer
        get() = tracerProvider.getTracer("test_tracer", "0.1.0")

    val logger: Logger
        get() = kotlinApi.loggerProvider.getLogger("test_logger")

    val objectCreator: ObjectCreator
        get() = kotlinApi.objectCreator

    internal fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null,
        sanitizeSpanContextIds: Boolean = true,
        assertions: (spans: List<SerializableSpanData>) -> Unit = {},
    ) {
        val observedSpans: List<OtelJavaSpanData> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { spanExporter.exportedSpans.map(SpanData::toOtelJavaSpanData) }
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
