package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SpanAttributesTest {

    private val expected = mapOf(
        "string" to "value",
        "double" to 3.14,
        "boolean" to true,
        "long" to 90000000000000,
        "string_list" to listOf("string"),
        "double_list" to listOf(3.14),
        "boolean_list" to listOf(true),
        "long_list" to listOf(90000000000000)
    )

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var tracer: TracerImpl

    @BeforeTest
    fun setUp() {
        tracer = TracerImpl(
            FakeClock(),
            FakeSpanProcessor(),
            FakeObjectCreator(),
            key,
            FakeResource(),
        )
    }

    @Test
    fun `test span default attributes`() {
        val span = tracer.createSpan("test")
        assertTrue(span.attributes.isEmpty())

        // ensure returned values is immutable, and not the underlying implementation
        assertTrue(span.attributes !is MutableMap<*, *>)
    }

    @Test
    fun `test span add attributes during creation`() {
        val span = tracer.createSpan("test") {
            addTestAttributes()
        }
        assertEquals(expected, span.attributes)
    }

    @Test
    fun `test span add attributes after creation`() {
        val span = tracer.createSpan("test")
        span.addTestAttributes()
        assertEquals(expected, span.attributes)
    }

    @Test
    fun `test span add attributes after end`() {
        val span = tracer.createSpan("test")
        span.addTestAttributes()
        assertEquals(expected, span.attributes)
        span.end()
        span.overrideTestAttributes()
        assertEquals(expected, span.attributes)
    }

    private fun MutableAttributeContainer.addTestAttributes() {
        setStringAttribute("string", "value")
        setDoubleAttribute("double", 3.14)
        setBooleanAttribute("boolean", true)
        setLongAttribute("long", 90000000000000)
        setStringListAttribute("string_list", listOf("string"))
        setDoubleListAttribute("double_list", listOf(3.14))
        setBooleanListAttribute("boolean_list", listOf(true))
        setLongListAttribute("long_list", listOf(90000000000000))
    }

    private fun MutableAttributeContainer.overrideTestAttributes() {
        setStringAttribute("string", "override")
        setDoubleAttribute("double", 5.4)
        setBooleanAttribute("boolean", false)
        setLongAttribute("long", 80000000000000)
        setStringListAttribute("string_list", listOf("override"))
        setDoubleListAttribute("double_list", listOf(5.4))
        setBooleanListAttribute("boolean_list", listOf(false))
        setLongListAttribute("long_list", listOf(80000000000000))
    }
}
