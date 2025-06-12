@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.testing.junit4

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanExporter
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.data.SpanData
import org.junit.rules.ExternalResource

/**
 * A JUnit4 rule which sets up an [OpenTelemetrySdk] for testing, resetting state before each test.
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
 *     tracer = otelTesting.getTracer("test")
 *   }
 *
 *   @Test
 *   fun test() {
 *     tracer.createSpan("hello").end()
 *     assertTrue(
 *          otelTesting.spans.any  { it.name == "hello" }
 *     )
 *   }
 * }
 * ```
 */
public class OpenTelemetryRule : ExternalResource() {

    private val spanExporter = InMemorySpanExporter()

    private val tracerProvider: SdkTracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(spanExporter))
        .build()

    private val sdk = io.opentelemetry.sdk.OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build()

    public val openTelemetry: OpenTelemetrySdk = OpenTelemetrySdk(sdk)

    public val spans: List<SpanData>
        get() = spanExporter.exportedSpans

    public fun getTracer(name: String): Tracer {
        return openTelemetry.tracerProvider.getTracer(name)
    }

    override fun before() {
        spanExporter.reset()
    }
}
