package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.k2j.InMemorySpanExporter
import io.embrace.opentelemetry.kotlin.k2j.InMemorySpanProcessor
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.toSerializable
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.data.SpanData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal class OtelKotlinHarness {

    private val exporter = InMemorySpanExporter()

    private val tracerProvider: SdkTracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(exporter))
        .setClock(FakeClock())
        .build()

    val sdk: OpenTelemetry = OpenTelemetrySdk.builder().setTracerProvider(
        tracerProvider
    ).build()

    val clock: ClockAdapter = ClockAdapter(FakeClock())

    private fun awaitSpans(expectedCount: Int, filter: (SpanData) -> Boolean = { true }): List<SpanData> {
        val supplier = { exporter.exportedSpans.filter(filter) }
        val tries = 1000
        val countDownLatch = CountDownLatch(1)
        repeat(tries) {
            if (supplier().size != expectedCount) {
                countDownLatch.await(1.toLong(), TimeUnit.MILLISECONDS)
            } else {
                return supplier()
            }
        }
        val spans = supplier()
        throw TimeoutException(
            "Timeout. Expected $expectedCount spans, but got ${spans.size}. " +
                "Found spans: ${spans.joinToString { it.name }}"
        )
    }

    internal fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null,
        spanDataFilter: (SpanData) -> Boolean = { true },
    ) {
        val observedSpans: List<SpanData> = awaitSpans(expectedCount, spanDataFilter)

        if (goldenFileName != null) {
            compareGoldenFile(observedSpans.map(SpanData::toSerializable), goldenFileName)
        }
    }
}
