package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalApi::class)
internal class SpanExtTest {

    @Test
    fun `record exception`() {
        val span = FakeSpan()
        assertEquals(0, span.events().size)

        span.recordException(IllegalArgumentException())
        span.recordException(IllegalStateException("Whoops!")) {
            setStringAttribute("extra", "value")
        }
        val events = span.events()
        assertEquals(2, events.size)

        val simple = events.first()
        assertEquals("exception", simple.name)
        val simpleAttrs = simple.attributes()
        assertEquals(2, simpleAttrs.size)
        assertEquals("java.lang.IllegalArgumentException", simpleAttrs["exception.type"])
        assertNotNull(simpleAttrs["exception.stacktrace"])

        val complex = events.last()
        assertEquals("exception", complex.name)
        val complexAttrs = complex.attributes()
        assertEquals(3, complexAttrs.size)
        assertEquals("java.lang.IllegalStateException", complexAttrs["exception.type"])
        assertEquals("Whoops!", complexAttrs["exception.message"])
        assertNotNull(complexAttrs["exception.stacktrace"])
    }
}
