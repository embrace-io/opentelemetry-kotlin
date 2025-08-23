package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.createObjectCreator
import io.embrace.opentelemetry.kotlin.init.config.LogLimitConfig
import io.embrace.opentelemetry.kotlin.init.config.LoggingConfig
import io.embrace.opentelemetry.kotlin.resource.ResourceImpl
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class LoggerProviderImplTest {

    private val clock = FakeClock()
    private val loggingConfig = LoggingConfig(
        emptyList(),
        LogLimitConfig(100, 100),
        ResourceImpl(emptyMap(), null)
    )
    private val objectCreator = createObjectCreator()

    @Test
    fun `test minimal logger provider`() {
        val impl = LoggerProviderImpl(clock, loggingConfig, objectCreator)
        assertNotNull(impl.getLogger(name = ""))
    }

    @Test
    fun `test full logger provider`() {
        val impl = LoggerProviderImpl(clock, loggingConfig, objectCreator)
        val first = impl.getLogger(
            name = "name",
            version = "0.1.0",
            schemaUrl = "https://example.com/foo"
        ) {
            setStringAttribute("key", "value")
        }
        assertNotNull(first)
    }

    @Test
    fun `test dupe logger provider name`() {
        val impl = LoggerProviderImpl(clock, loggingConfig, objectCreator)
        val first = impl.getLogger(name = "name")
        val second = impl.getLogger(name = "name")
        val third = impl.getLogger(name = "other")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe logger provider version`() {
        val impl = LoggerProviderImpl(clock, loggingConfig, objectCreator)
        val first = impl.getLogger(name = "name", version = "0.1.0")
        val second = impl.getLogger(name = "name", version = "0.1.0")
        val third = impl.getLogger(name = "name", version = "0.2.0")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe logger provider schemaUrl`() {
        val impl = LoggerProviderImpl(clock, loggingConfig, objectCreator)
        val first = impl.getLogger(name = "name", schemaUrl = "https://example.com/foo")
        val second = impl.getLogger(name = "name", schemaUrl = "https://example.com/foo")
        val third = impl.getLogger(name = "name", schemaUrl = "https://example.com/bar")
        assertSame(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `test dupe logger provider attributes`() {
        val impl = LoggerProviderImpl(clock, loggingConfig, objectCreator)
        val first = impl.getLogger(name = "name") {
            setStringAttribute("key", "value")
        }
        val second = impl.getLogger(name = "name") {
            setStringAttribute("key", "value")
        }
        val third = impl.getLogger(name = "name") {
            setStringAttribute("foo", "bar")
        }
        assertSame(first, second)
        assertNotEquals(first, third)
    }
}
