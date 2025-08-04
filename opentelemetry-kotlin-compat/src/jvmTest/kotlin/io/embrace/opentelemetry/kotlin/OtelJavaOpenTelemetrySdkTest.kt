package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextPropagators
import io.embrace.opentelemetry.kotlin.logging.FakeLoggerProvider
import io.embrace.opentelemetry.kotlin.logging.OtelJavaLoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.FakeTracerProvider
import io.embrace.opentelemetry.kotlin.tracing.OtelJavaTracerProviderAdapter
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class OtelJavaOpenTelemetrySdkTest {

    @Test
    fun `test entrypoint`() {
        val tracerProvider = OtelJavaTracerProviderAdapter(FakeTracerProvider())
        val loggerProvider = OtelJavaLoggerProviderAdapter(FakeLoggerProvider())

        val otel = OtelJavaOpenTelemetrySdk(tracerProvider, loggerProvider)
        assertSame(tracerProvider, otel.tracerProvider)
        assertSame(loggerProvider, otel.logsBridge)
        assertSame(otel.propagators, OtelJavaContextPropagators.noop())
    }
}
