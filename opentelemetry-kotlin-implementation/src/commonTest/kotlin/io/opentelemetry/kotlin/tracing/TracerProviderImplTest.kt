package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.clock.FakeClock
import io.opentelemetry.kotlin.creator.FakeObjectCreator
import io.opentelemetry.kotlin.init.config.TracingConfig
import io.opentelemetry.kotlin.resource.ResourceImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class TracerProviderImplTest {

    private val tracingConfig = TracingConfig(
        emptyList(),
        fakeSpanLimitsConfig,
        ResourceImpl(emptyMap(), null)
    )

    private lateinit var impl: TracerProvider

    @BeforeTest
    fun setUp() {
        impl = TracerProviderImpl(FakeClock(), tracingConfig, FakeObjectCreator())
    }

    @Test
    fun `test minimal tracer provider`() {
        assertNotNull(impl.getTracer(name = ""))
    }

    @Test
    fun `test full tracer provider`() {
        val first = impl.getTracer(
            name = "name",
            version = "0.1.0",
            schemaUrl = "https://example.com/foo"
        ) {
            setStringAttribute("key", "value")
        }
        assertNotNull(first)
    }

    @Test
    fun `test dupe tracer provider name`() {
        val first = impl.getTracer(name = "name")
        val second = impl.getTracer(name = "name")
        val third = impl.getTracer(name = "other")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe tracer provider version`() {
        val first = impl.getTracer(name = "name", version = "0.1.0")
        val second = impl.getTracer(name = "name", version = "0.1.0")
        val third = impl.getTracer(name = "name", version = "0.2.0")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe tracer provider schemaUrl`() {
        val first = impl.getTracer(name = "name", schemaUrl = "https://example.com/foo")
        val second = impl.getTracer(name = "name", schemaUrl = "https://example.com/foo")
        val third = impl.getTracer(name = "name", schemaUrl = "https://example.com/bar")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe tracer provider attributes`() {
        val first = impl.getTracer(name = "name") {
            setStringAttribute("key", "value")
        }
        val second = impl.getTracer(name = "name") {
            setStringAttribute("key", "value")
        }
        val third = impl.getTracer(name = "name") {
            setStringAttribute("foo", "bar")
        }
        assertSame(first, second)
        assertNotEquals(first, third)
    }
}
