package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.FakeReadWriteSpan
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SimpleSpanProcessorTest {

    @Test
    fun testSpanProcessorDefaults() {
        val processor = SimpleSpanProcessor(FakeSpanExporter())
        assertTrue(processor.isStartRequired())
        assertTrue(processor.isEndRequired())
        assertEquals(OperationResultCode.Success, processor.shutdown())
        assertEquals(OperationResultCode.Success, processor.forceFlush())
    }

    @Test
    fun testSpanProcessorExport() {
        val exporter = FakeSpanExporter()
        val processor = SimpleSpanProcessor(exporter)
        val span = FakeReadWriteSpan()
        processor.onStart(span, FakeContext())
        processor.onEnd(span)

        val export = exporter.exports.single()
        assertEquals(span.name, export.name)
    }
}
