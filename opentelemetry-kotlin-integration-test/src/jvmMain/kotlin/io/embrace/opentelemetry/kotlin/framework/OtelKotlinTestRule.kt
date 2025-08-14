package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Contains business logic for writing integration tests that assert the behavior of the
 * opentelemetry-kotlin API.
 */
@OptIn(ExperimentalApi::class)
abstract class OtelKotlinTestRule {

    private companion object {
        private const val EXPORT_POLL_ATTEMPTS = 1000
        private const val EXPORT_POLL_WAIT_US = 10L
    }

    /**
     * Reference to an instance of the opentelemetry-kotlin API. Implementations should pass in
     * [tracerProviderConfig], [loggerProviderConfig], and [clock].
     */
    abstract val kotlinApi: OpenTelemetry

    /**
     * Configuration for the test harness that specifies how the API should behave.
     */
    val config: TestHarnessConfig = TestHarnessConfig()

    /**
     * Fake clock used by the test harness.
     */
    val clock: FakeClock = FakeClock()

    private val spanExporter = InMemorySpanExporter()
    private val logRecordExporter = InMemoryLogRecordExporter()

    /**
     * Configuration for the logger provider that adds test hooks to obtain exported logs.
     */
    protected val loggerProviderConfig: LoggerProviderConfigDsl.() -> Unit = {
        config.attributes?.let { resource(config.schemaUrl, it) }
        addLogRecordProcessor(InMemoryLogRecordProcessor(logRecordExporter))
        config.logRecordProcessors.forEach { addLogRecordProcessor(it) }
        logLimits(config.logLimits)
    }

    /**
     * Configuration for the tracer provider that adds test hooks to obtain exported logs.
     */
    protected val tracerProviderConfig: TracerProviderConfigDsl.() -> Unit = {
        config.attributes?.let { resource(config.schemaUrl, it) }
        addSpanProcessor(InMemorySpanProcessor(spanExporter))
        config.spanProcessors.forEach { addSpanProcessor(it) }
        spanLimits(config.spanLimits)
    }

    /**
     * Syntactic sugar to obtain a tracer provider from the API.
     */
    val tracerProvider: TracerProvider by lazy { kotlinApi.tracerProvider }

    /**
     * Syntactic sugar to obtain a tracer from the API.
     */
    val tracer: Tracer by lazy { tracerProvider.getTracer("test_tracer", "0.1.0") }

    /**
     * Syntactic sugar to obtain a logger from the API.
     */
    val logger: Logger by lazy { kotlinApi.loggerProvider.getLogger("test_logger") }

    /**
     * Syntactic sugar to obtain an object creator from the API.
     */
    val objectCreator: ObjectCreator by lazy { kotlinApi.objectCreator }

    /**
     * Asserts that log records were exported correctly. A custom assertion can be provided as a lambda,
     * and the exported log records are checked against a known golden file output of JSON.
     */
    fun assertLogRecords(
        expectedCount: Int,
        goldenFileName: String? = null,
        assertions: (logs: List<ReadableLogRecord>) -> Unit = {},
    ) {
        val observedLogRecords: List<ReadableLogRecord> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { logRecordExporter.exportedLogRecords }
        )
        val data = observedLogRecords.map(ReadableLogRecord::toSerializable)
        assertions(observedLogRecords)

        if (goldenFileName != null) {
            compareGoldenFile(data, goldenFileName)
        }
    }

    /**
     * Asserts that spans were exported correctly. A custom assertion can be provided as a lambda,
     * and the exported spans are checked against a known golden file output of JSON.
     */
    fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null,
        assertions: (spans: List<SpanData>) -> Unit = {},
    ) {
        val observedSpans: List<SpanData> = awaitExportedData(
            expectedCount = expectedCount,
            supplier = { spanExporter.exportedSpans }
        )
        val data = observedSpans.map(SpanData::toSerializable)
        assertions(observedSpans)

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
            "Timeout. Expected $expectedCount elements, but got ${data.size}. " + "Found: ${data.joinToString { it.toString() }}"
        )
    }
}
