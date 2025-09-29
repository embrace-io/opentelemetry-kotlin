package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.assertAttributesMatch
import io.embrace.opentelemetry.kotlin.factory.toHexString
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalApi::class)
class LogRecordProtobufConversionTest {

    @Test
    fun testEmptyConversion() {
        val obj = FakeReadableLogRecord(
            timestamp = null,
            observedTimestamp = null,
            severityNumber = null,
            severityText = null,
            body = null,
            attributes = emptyMap(),
        )
        val protobuf = obj.toProtobuf()
        assertEquals(0, protobuf.timeUnixNano)
        assertEquals(0, protobuf.observedTimeUnixNano)
        assertFalse(protobuf.body.hasStringValue())
        assertEquals(obj.spanContext.traceId, protobuf.traceId.toByteArray().toHexString())
        assertEquals(obj.spanContext.spanId, protobuf.spanId.toByteArray().toHexString())
        assertEquals("", protobuf.severityText)
        assertEquals(0, protobuf.severityNumber.number)
        assertAttributesMatch(obj.attributes, protobuf.attributesList)
    }

    @Test
    fun testNonDefaultConversion() {
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
        val obj = FakeReadableLogRecord(attributes = attrs)
        val protobuf = obj.toProtobuf()
        assertEquals(obj.timestamp, protobuf.timeUnixNano)
        assertEquals(obj.observedTimestamp, protobuf.observedTimeUnixNano)
        assertEquals(obj.body, protobuf.body.stringValue)
        assertEquals(obj.spanContext.traceId, protobuf.traceId.toByteArray().toHexString())
        assertEquals(obj.spanContext.spanId, protobuf.spanId.toByteArray().toHexString())
        assertEquals(obj.severityText, protobuf.severityText)
        assertEquals(obj.severityNumber?.severityNumber, protobuf.severityNumber.number)
        assertAttributesMatch(obj.attributes, protobuf.attributesList)
    }
}
