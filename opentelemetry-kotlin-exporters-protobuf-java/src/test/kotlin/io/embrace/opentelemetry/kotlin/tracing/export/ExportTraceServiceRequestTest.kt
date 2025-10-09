package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.factory.toHexString
import io.embrace.opentelemetry.kotlin.tracing.data.FakeSpanData
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
class ExportTraceServiceRequestTest {

    @Test
    fun testCreateExportTraceServiceRequest() {
        val spanData = FakeSpanData()
        val request = listOf(spanData).toExportTraceServiceRequest()
        assertEquals(1, request.resourceSpansCount)
        val resourceSpans = request.resourceSpansList[0]

        assertEquals(1, resourceSpans.scopeSpansCount)
        val scopeSpans = resourceSpans.scopeSpansList[0]

        assertEquals(1, scopeSpans.spansCount)
        val span = scopeSpans.spansList[0]

        assertEquals(spanData.spanContext.traceId, span.traceId.toByteArray().toHexString())
    }
}
