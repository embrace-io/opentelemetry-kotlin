package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaLogRecordExporter
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class LogRecordExporterAdapterTest {

    private lateinit var impl: FakeOtelJavaLogRecordExporter
    private lateinit var wrapper: LogRecordExporterAdapter

    @Before
    fun setUp() {
        impl = FakeOtelJavaLogRecordExporter()
        wrapper = LogRecordExporterAdapter(impl)
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
