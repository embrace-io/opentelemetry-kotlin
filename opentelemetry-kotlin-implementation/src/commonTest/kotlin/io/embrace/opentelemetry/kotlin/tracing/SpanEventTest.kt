package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanEventTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeSpanProcessor

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        tracer = TracerImpl(
            clock,
            processor,
            FakeObjectCreator(),
            key,
            FakeResource()
        )
    }

    @Test
    fun `test span event`() {
        clock.time = 2
        tracer.createSpan("test").apply {
            addEvent("event")
            addEvent("event2", 5)
            addEvent("event3", 10) {
                setStringAttribute("foo", "bar")
            }
            end()
        }

        val events = retrieveEvents(3)
        assertEventData(events[0], "event", clock.time, emptyMap())
        assertEventData(events[1], "event2", 5, emptyMap())
        assertEventData(events[2], "event3", 10, mapOf("foo" to "bar"))
    }

    @Test
    fun `test two span events with same keys`() {
        tracer.createSpan("test").apply {
            addEvent("event")
            addEvent("event")
            end()
        }
        val events = retrieveEvents(2)
        assertEventData(events[0], "event", clock.time, emptyMap())
        assertEventData(events[1], "event", clock.time, emptyMap())
    }

    @Test
    fun `test span event after end`() {
        tracer.createSpan("test").apply {
            end()
            addEvent("event")
        }
        retrieveEvents(0)
    }

    private fun retrieveEvents(expected: Int): List<EventData> {
        val events = processor.endCalls.single().events
        assertEquals(expected, events.size)
        return events
    }

    private fun assertEventData(
        event: EventData,
        name: String,
        time: Long,
        attrs: Map<String, Any>
    ) {
        assertEquals(name, event.name)
        assertEquals(time, event.timestamp)
        assertEquals(attrs, event.attributes)
    }
}
