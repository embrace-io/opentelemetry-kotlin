package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.factory.FakeSdkFactory
import io.embrace.opentelemetry.kotlin.init.config.SpanLimitConfig
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanEventTest {

    private val eventLimit = 3
    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeSpanProcessor
    private lateinit var spanLimitConfig: SpanLimitConfig

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        spanLimitConfig = SpanLimitConfig(
            attributeCountLimit = fakeSpanLimitsConfig.attributeCountLimit,
            linkCountLimit = fakeSpanLimitsConfig.linkCountLimit,
            eventCountLimit = eventLimit,
            attributeCountPerEventLimit = fakeSpanLimitsConfig.attributeCountPerEventLimit,
            attributeCountPerLinkLimit = fakeSpanLimitsConfig.attributeCountPerLinkLimit
        )
        tracer = TracerImpl(
            clock,
            processor,
            FakeSdkFactory(),
            key,
            FakeResource(),
            spanLimitConfig
        )
    }

    @Test
    fun testSpanEvent() {
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
    fun testTwoEventsWithSameKey() {
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
    fun testSpanEventAfterEnd() {
        tracer.createSpan("test").apply {
            end()
            addEvent("event")
        }
        retrieveEvents(0)
    }

    @Test
    fun testSpanEventDuringCreation() {
        clock.time = 2
        tracer.createSpan("test", action = {
            addEvent("event")
            addEvent("event2", 5)
            addEvent("event3", 10) {
                setStringAttribute("foo", "bar")
            }
        }).apply {
            end()
        }

        val events = retrieveEvents(3)
        assertEventData(events[0], "event", clock.time, emptyMap())
        assertEventData(events[1], "event2", 5, emptyMap())
        assertEventData(events[2], "event3", 10, mapOf("foo" to "bar"))
    }

    @Test
    fun testEventsLimitNotExceeded() {
        tracer.createSpan("test", action = {
            repeat(eventLimit + 1) {
                addEvent("event")
            }
        }).apply {
            end()
        }

        retrieveEvents(3)
    }

    @Test
    fun testEventsLimitNotExceeded2() {
        tracer.createSpan("test").apply {
            repeat(eventLimit + 1) {
                addEvent("event")
            }
            end()
        }

        retrieveEvents(3)
    }

    @Test
    fun testSpanEventAttributesLimit() {
        val span = tracer.createSpan("test", action = {
            addEvent("event") {
                repeat(fakeSpanLimitsConfig.attributeCountLimit + 1) {
                    setStringAttribute("foo$it", "bar")
                }
            }
        })
        val event = span.events.single()
        assertEquals(fakeSpanLimitsConfig.attributeCountLimit, event.attributes.size)
    }

    @Test
    fun testSpanEventAttributesLimit2() {
        val span = tracer.createSpan("test").apply {
            addEvent("event", attributes = {
                repeat(fakeSpanLimitsConfig.attributeCountLimit + 1) {
                    setStringAttribute("foo$it", "bar")
                }
            })
        }
        val event = span.events.single()
        assertEquals(fakeSpanLimitsConfig.attributeCountLimit, event.attributes.size)
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
