package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.assertAttributesMatch
import io.embrace.opentelemetry.kotlin.factory.toHexString
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.FakeSpanData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.opentelemetry.proto.trace.v1.Span
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
class SpanProtobufConversionTest {

    @Test
    fun testConversion() {
        val attrs = mapOf(
            "string" to "value",
            "long" to 5L,
            "double" to 10.0,
            "bool" to true,
            "stringList" to listOf("a", "b"),
            "longList" to listOf(5, 10L),
            "doubleList" to listOf(6.0, 12.0),
            "boolList" to listOf(true, false),
        )
        val obj = FakeSpanData(
            attributes = attrs,
            status = StatusData.Error("Whoops")
        )
        val protobuf = obj.toProtobuf()

        assertEquals(obj.name, protobuf.name)
        assertEquals(obj.spanContext.traceId, protobuf.traceId.toByteArray().toHexString())
        assertEquals(obj.spanContext.spanId, protobuf.spanId.toByteArray().toHexString())
        assertEquals(obj.startTimestamp, protobuf.startTimeUnixNano)
        assertEquals(obj.endTimestamp, protobuf.endTimeUnixNano)
        assertEquals(obj.status.statusCode.ordinal, protobuf.status.codeValue)
        assertEquals(obj.status.description, protobuf.status.message)
        assertAttributesMatch(obj.attributes, protobuf.attributesList)
        assertEventsMatch(obj.events, protobuf.eventsList)
        assertLinksMatch(obj.links, protobuf.linksList)
    }

    private fun assertEventsMatch(
        events: List<EventData>,
        eventsList: List<Span.Event>
    ) {
        assertEquals(events.size, eventsList.size)
        events.forEachIndexed { index, event ->
            val proto = eventsList[index]
            assertEquals(event.name, proto.name)
            assertEquals(event.timestamp, proto.timeUnixNano)
            assertAttributesMatch(event.attributes, proto.attributesList)
        }
    }

    private fun assertLinksMatch(
        links: List<LinkData>,
        linksList: List<Span.Link>
    ) {
        assertEquals(links.size, linksList.size)
        links.forEachIndexed { index, link ->
            val proto = linksList[index]
            assertEquals(link.spanContext.traceId, proto.traceId.toByteArray().toHexString())
            assertEquals(link.spanContext.spanId, proto.spanId.toByteArray().toHexString())
            assertAttributesMatch(link.attributes, proto.attributesList)
        }
    }
}
