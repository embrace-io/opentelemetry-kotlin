@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.testing.junit4

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanExporter
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanProcessor
import io.embrace.opentelemetry.kotlin.toOtelKotlinApi
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import org.junit.rules.ExternalResource

/**
 * A JUnit4 rule which sets up an [OpenTelemetry] instance for testing, resetting state before each test.
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
class OpenTelemetryRule : ExternalResource() {

    private val spanExporter = InMemorySpanExporter()

    private val tracerProvider: OtelJavaSdkTracerProvider = OtelJavaSdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(spanExporter))
        .build()

    private val sdk = OtelJavaOpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build()

    val openTelemetry: OpenTelemetry = sdk.toOtelKotlinApi()

    val spans: List<OtelJavaSpanData>
        get() = spanExporter.exportedSpans

    fun getTracer(name: String): Tracer {
        return openTelemetry.tracerProvider.getTracer(name)
    }

    override fun before() {
        spanExporter.reset()
    }
}
