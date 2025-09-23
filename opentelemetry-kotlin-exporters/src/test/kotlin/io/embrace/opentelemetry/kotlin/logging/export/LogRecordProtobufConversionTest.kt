package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import io.opentelemetry.proto.common.v1.KeyValue
import io.opentelemetry.proto.logs.v1.LogRecord
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
        assertEquals(obj.spanContext.traceId, protobuf.traceId.toStringUtf8())
        assertEquals(obj.spanContext.spanId, protobuf.spanId.toStringUtf8())
        assertEquals("", protobuf.severityText)
        assertEquals(0, protobuf.severityNumber.number)
        assertAttributesMatch(obj, protobuf)
    }

    @Test
    fun testNonDefaultConversion() {
        val attrs = mapOf(
            "string" to "value",
            "long" to 5L,
            "double" to 10.0,
            "bool" to true,
        )
        val obj = FakeReadableLogRecord(attributes = attrs)
        val protobuf = obj.toProtobuf()
        assertEquals(obj.timestamp, protobuf.timeUnixNano)
        assertEquals(obj.observedTimestamp, protobuf.observedTimeUnixNano)
        assertEquals(obj.body, protobuf.body.stringValue)
        assertEquals(obj.spanContext.traceId, protobuf.traceId.toStringUtf8())
        assertEquals(obj.spanContext.spanId, protobuf.spanId.toStringUtf8())
        assertEquals(obj.severityText, protobuf.severityText)
        assertEquals(obj.severityNumber?.severityNumber, protobuf.severityNumber.number)
        assertAttributesMatch(obj, protobuf)
    }

    private fun assertAttributesMatch(
        log: FakeReadableLogRecord,
        protobuf: LogRecord
    ) {
        assertEquals(log.attributes.size, protobuf.attributesCount)
        protobuf.attributesList.forEach { attr ->
            val expected = log.attributes[attr.key]
            val observed = retrieveValue(attr)
            assertEquals(expected, observed)
        }
    }

    private fun retrieveValue(attr: KeyValue): Any {
        val obj = attr.value
        return when {
            obj.hasStringValue() -> obj.stringValue
            obj.hasIntValue() -> obj.intValue
            obj.hasDoubleValue() -> obj.doubleValue
            obj.hasBoolValue() -> obj.boolValue
            else -> error("Unknown type!")
        }
    }
}
