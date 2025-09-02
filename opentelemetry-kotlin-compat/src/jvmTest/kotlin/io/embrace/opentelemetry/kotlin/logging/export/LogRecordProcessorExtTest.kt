package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaLogRecordProcessor
import io.embrace.opentelemetry.kotlin.framework.OtelKotlinHarness
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class LogRecordProcessorExtTest {

    @Test
    fun testOnEmit() {
        val impl = FakeOtelJavaLogRecordProcessor()
        val adapter = impl.toOtelKotlinLogRecordProcessor()
        val harness = OtelKotlinHarness()
        harness.config.logRecordProcessors.add(adapter)

        val logger = harness.javaApi.logsBridge.get("logger")
        val msg = "hello world"
        logger.logRecordBuilder()
            .setBody(msg)
            .emit()

        val export = impl.exports.single()
        assertEquals(msg, export.bodyValue?.asString())
    }

    @Test
    fun testFlush() {
        val impl = FakeOtelJavaLogRecordProcessor()
        val adapter = impl.toOtelKotlinLogRecordProcessor()
        adapter.forceFlush()
        assertEquals(1, impl.flushCount)
    }

    @Test
    fun testShutdown() {
        val impl = FakeOtelJavaLogRecordProcessor()
        val adapter = impl.toOtelKotlinLogRecordProcessor()
        adapter.shutdown()
        assertEquals(1, impl.shutdownCount)
    }
}
