package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.init.config.SpanLimitConfig
import io.embrace.opentelemetry.kotlin.init.config.TracingConfig
import io.embrace.opentelemetry.kotlin.resource.ResourceImpl
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class TracerProviderImplTest {

    private val tracingConfig = TracingConfig(
        emptyList(),
        SpanLimitConfig(100, 100, 100, 100, 100),
        ResourceImpl(emptyMap(), null)
    )

    @Test
    fun `test minimal tracer provider`() {
        val impl = TracerProviderImpl(tracingConfig)
        assertNotNull(impl.getTracer(name = ""))
    }

    @Test
    fun `test full tracer provider`() {
        val impl = TracerProviderImpl(tracingConfig)
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
        val impl = TracerProviderImpl(tracingConfig)
        val first = impl.getTracer(name = "name")
        val second = impl.getTracer(name = "name")
        val third = impl.getTracer(name = "other")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe tracer provider version`() {
        val impl = TracerProviderImpl(tracingConfig)
        val first = impl.getTracer(name = "name", version = "0.1.0")
        val second = impl.getTracer(name = "name", version = "0.1.0")
        val third = impl.getTracer(name = "name", version = "0.2.0")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe tracer provider schemaUrl`() {
        val impl = TracerProviderImpl(tracingConfig)
        val first = impl.getTracer(name = "name", schemaUrl = "https://example.com/foo")
        val second = impl.getTracer(name = "name", schemaUrl = "https://example.com/foo")
        val third = impl.getTracer(name = "name", schemaUrl = "https://example.com/bar")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe tracer provider attributes`() {
        val impl = TracerProviderImpl(tracingConfig)
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
