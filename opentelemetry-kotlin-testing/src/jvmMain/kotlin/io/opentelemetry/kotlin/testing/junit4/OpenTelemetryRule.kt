@file:OptIn(ExperimentalApi::class)

package io.opentelemetry.kotlin.testing.junit4

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.OpenTelemetry
import io.opentelemetry.kotlin.OpenTelemetryInstance
import io.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.opentelemetry.kotlin.decorateJavaApi
import io.opentelemetry.kotlin.getTracer
import io.opentelemetry.kotlin.testing.common.InMemorySpanExporter
import io.opentelemetry.kotlin.testing.common.InMemorySpanProcessor
import io.opentelemetry.kotlin.tracing.Tracer
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

    private val tracerProvider: OtelJavaSdkTracerProvider = OtelJavaSdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(spanExporter))
        .build()

    private val sdk = OtelJavaOpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build()

    public val openTelemetry: OpenTelemetry = OpenTelemetryInstance.decorateJavaApi(sdk)

    public val spans: List<OtelJavaSpanData>
        get() = spanExporter.exportedSpans

    public fun getTracer(name: String): Tracer {
        return openTelemetry.getTracer(name)
    }

    override fun before() {
        spanExporter.reset()
    }
}
