@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.testing.junit4

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanExporter
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanProcessor
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.data.SpanData
import org.junit.rules.ExternalResource

/**
 * A JUnit4 rule which sets up the [TracerProvider] for testing, resetting state between
 * tests. This rule cannot be used with [org.junit.ClassRule].
 *
 * ```kotlin
 * class CoolTest {
 *   @get:Rule
 *   val otelTesting = OpenTelemetryRule()
 *
 *   private lateinit var tracer: Tracer
 *
 *   @Before
 *   fun setUp() {
 *     tracer = otelTesting.tracerProviderAdapter.getTracer("test")
 *   }
 *
 *   @Test
 *   fun test() {
 *     tracer.spanBuilder("name").startSpan().end()
 *     assertEquals(expected, otelTesting.spans)
 *   }
 * }
 * ```
 */
public class OpenTelemetryRule : ExternalResource() {

    private val spanExporter = InMemorySpanExporter()

    private val tracerProvider: SdkTracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(spanExporter))
        .build()

    public val tracerProviderAdapter: TracerProviderAdapter = TracerProviderAdapter(
        tracerProvider
    )

    public val spans: List<SpanData>
        get() = spanExporter.exportedSpans

    override fun before() {
        spanExporter.reset()
    }
} 