package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaLogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class LogRecordProcessorAdapterTest {

    private lateinit var wrapper: LogRecordProcessor
    private lateinit var impl: FakeOtelJavaLogRecordProcessor

    @Before
    fun setUp() {
        impl = FakeOtelJavaLogRecordProcessor()
        wrapper = LogRecordProcessorAdapter(impl)
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
}
