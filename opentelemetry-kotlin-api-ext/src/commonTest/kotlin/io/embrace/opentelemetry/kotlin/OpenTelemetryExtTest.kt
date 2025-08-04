package io.embrace.opentelemetry.kotlin

import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class OpenTelemetryExtTest {

    @Test
    fun `test get tracer`() {
        val otel = FakeOpenTelemetry()
        val name = "my_tracer"
        val expected = otel.tracerProvider.getTracer(name)
        val observed = otel.getTracer(name)
        assertSame(expected, observed)
    }

    @Test
    fun `test get logger`() {
        val otel = FakeOpenTelemetry()
        val name = "my_logger"
        val expected = otel.loggerProvider.getLogger(name)
        val observed = otel.getLogger(name)
        assertSame(expected, observed)
    }
}
