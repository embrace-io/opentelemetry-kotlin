package io.embrace.opentelemetry.kotlin.k2j

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.data.SpanData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal class OtelJavaHarness {

    private val exporter = InMemorySpanExporter()

    val sdk: OpenTelemetry = OpenTelemetrySdk.builder().setTracerProvider(
        SdkTracerProvider.builder().addSpanProcessor(InMemorySpanProcessor(exporter)).build()
    ).build()


    fun awaitSpans(expectedCount: Int, filter: (SpanData) -> Boolean = { true }): List<SpanData> {
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
}
