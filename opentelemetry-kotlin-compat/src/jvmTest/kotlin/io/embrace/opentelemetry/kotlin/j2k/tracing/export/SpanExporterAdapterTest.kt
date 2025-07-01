package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaSpanExporter
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class SpanExporterAdapterTest {

    private lateinit var impl: FakeOtelJavaSpanExporter
    private lateinit var wrapper: SpanExporterAdapter

    @Before
    fun setUp() {
        impl = FakeOtelJavaSpanExporter()
        wrapper = SpanExporterAdapter(impl)
    }

    @Test
    fun `test flush`() {
        assertEquals(OperationResultCode.Success, wrapper.forceFlush())
        assertEquals(1, impl.flushCount)
    }

    @Test
    fun `test shutdown`() {
        assertEquals(OperationResultCode.Success, wrapper.shutdown())
        assertEquals(1, impl.shutdownCount)
    }

    @Test
    fun `test export`() {
        TODO("Not yet implemented")
    }
}
