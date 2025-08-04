package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaSpanExporter
import io.embrace.opentelemetry.kotlin.tracing.data.FakeSpanData
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanExporterExtTest {

    @Test
    fun toOtelKotlinSpanExporter() {
        val impl = FakeOtelJavaSpanExporter()
        val adapter = impl.toOtelKotlinSpanExporter()
        adapter.export(mutableListOf(FakeSpanData()))

        val export = impl.exports.single()
        assertEquals("span", export.name)
    }
}
