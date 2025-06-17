@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.testing.junit5

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanExporter
import io.embrace.opentelemetry.kotlin.testing.common.InMemorySpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * A JUnit5 extension which sets up an [OpenTelemetrySdk] for testing, resetting state before each test.
 *
 * ```kotlin
 * @ExtendWith(OpenTelemetryExtension::class)
 * class CoolTest {
 *
 *   private lateinit var tracer: Tracer
 *
 *   @BeforeEach
 *   fun setUp(extension: OpenTelemetryExtension) {
 *     tracer = extension.getTracer("test")
 *   }
 *
 *   @Test
 *   fun test(extension: OpenTelemetryExtension) {
 *     tracer.createSpan("hello").end()
 *     assertTrue(
 *          extension.spans.any  { it.name == "hello" }
 *     )
 *   }
 * }
 * ```
 */
public class OpenTelemetryExtension : BeforeEachCallback {

    private val spanExporter = InMemorySpanExporter()

    private val tracerProvider: OtelJavaSdkTracerProvider = OtelJavaSdkTracerProvider.builder()
        .addSpanProcessor(InMemorySpanProcessor(spanExporter))
        .build()

    private val sdk = OtelJavaOpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build()

    public val openTelemetry: OpenTelemetrySdk = OpenTelemetrySdk(sdk)

    public val spans: List<OtelJavaSpanData>
        get() = spanExporter.exportedSpans

    public fun getTracer(name: String): Tracer {
        return openTelemetry.tracerProvider.getTracer(name)
    }

    override fun beforeEach(context: ExtensionContext?) {
        spanExporter.reset()
    }
}
