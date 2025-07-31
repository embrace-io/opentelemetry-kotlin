package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.creator.createCompatObjectCreator
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeClock
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class OpenTelemetrySdkTest {

    @Test
    fun `retrieve tracer provider`() {
        val sdk = OpenTelemetrySdk(
            OtelJavaOpenTelemetry.noop(),
            FakeClock(),
            createCompatObjectCreator()
        )
        val provider = sdk.tracerProvider
        val a = provider.getTracer("test")
        val b = provider.getTracer("test")
        val c = provider.getTracer("test", "1.0.0") {
            setStringAttribute("key", "value")
        }
        val d = provider.getTracer("another")
        assertSame(a, b)
        assertNotSame(b, c)
        assertNotSame(c, d)
    }

    @Test
    fun `retrieve logger provider`() {
        val sdk = OpenTelemetrySdk(
            OtelJavaOpenTelemetry.noop(),
            FakeClock(),
            createCompatObjectCreator()
        )
        val provider = sdk.loggerProvider
        val a = provider.getLogger("test")
        val b = provider.getLogger("test")
        val c = provider.getLogger("test", "1.0.0") {
            setStringAttribute("key", "value")
        }
        val d = provider.getLogger("another")
        assertSame(a, b)
        assertNotSame(b, c)
        assertNotSame(c, d)
    }
}
