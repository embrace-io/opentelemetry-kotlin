package io.embrace.opentelemetry.kotlin.integration.test

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.default
import io.embrace.opentelemetry.kotlin.framework.compareGoldenFile
import io.embrace.opentelemetry.kotlin.integration.test.model.toSerializable
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import kotlin.test.assertEquals

/**
 * Configures opentelemetry-kotlin to run for integration tests so that exported logs/traces
 * can be verified against expected output.
 */
@OptIn(ExperimentalApi::class)
internal class IntegrationTestHarness {

    private val spanExporter: FakeSpanProcessor = FakeSpanProcessor()
    private val spanProcessor: FakeSpanProcessor = FakeSpanProcessor(endAction = {
        spanExporter.onEnd(it)
    })
    private val logRecordProcessor: FakeLogRecordProcessor = FakeLogRecordProcessor()

    private val impl by lazy {
        OpenTelemetryInstance.default(
            tracerProvider = {
                addSpanProcessor(spanProcessor)
            },
            loggerProvider = {
                addLogRecordProcessor(logRecordProcessor)
            },
            clock = clock,
        )
    }

    /**
     * A tracer that can be used to create spans for test cases.
     */
    val tracer: Tracer by lazy { impl.tracerProvider.getTracer("key") }

    /**
     * A clock whose time can be adjusted for test cases.
     */
    val clock: FakeClock = FakeClock()

    fun assertSpans(
        expected: Int,
        goldenFileName: String,
        assertAction: (data: List<ReadableSpan>) -> Unit = {}
    ) {
        val endedSpans = spanExporter.endCalls.toList()
        assertEquals(expected, endedSpans.size)
        assertAction(endedSpans)

        val observedSpans = endedSpans.map(ReadableSpan::toSerializable)
        compareGoldenFile(observedSpans, goldenFileName)
    }
}
