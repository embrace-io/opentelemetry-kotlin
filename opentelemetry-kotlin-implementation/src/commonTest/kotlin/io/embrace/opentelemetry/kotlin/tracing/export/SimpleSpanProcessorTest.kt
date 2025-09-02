package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.error.FakeSdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.FakeReadWriteSpan
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SimpleSpanProcessorTest {

    private val fakeSpan = FakeReadWriteSpan()
    private val fakeContext = FakeContext()
    private lateinit var errorHandler: FakeSdkErrorHandler
    private lateinit var exporter: FakeSpanExporter

    @BeforeTest
    fun setUp() {
        errorHandler = FakeSdkErrorHandler()
        exporter = FakeSpanExporter()
    }

    @Test
    fun testOnStartDoesNothing() {
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        processor.onStart(fakeSpan, fakeContext)
        
        assertTrue(exporter.exports.isEmpty())
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testOnEndExportsSpan() {
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        processor.onEnd(fakeSpan)
        
        assertEquals(1, exporter.exports.size)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testOnEndWithExportFailure() {
        exporter = FakeSpanExporter(exportReturnValue = { OperationResultCode.Failure })
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        
        processor.onEnd(fakeSpan)
        
        assertEquals(1, exporter.exports.size)
        assertTrue(errorHandler.hasErrors())
    }

    @Test
    fun testOnEndWithExportException() {
        exporter = FakeSpanExporter(exportReturnValue = { throw RuntimeException("Export failed") })
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        
        processor.onEnd(fakeSpan)
        
        assertEquals(1, exporter.exports.size)
        assertTrue(errorHandler.hasErrors())
    }

    @Test
    fun testIsStartRequiredReturnsFalse() {
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        assertFalse(processor.isStartRequired())
    }

    @Test
    fun testIsEndRequiredReturnsTrue() {
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        assertTrue(processor.isEndRequired())
    }

    @Test
    fun testForceFlushCallsExporter() {
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        val result = processor.forceFlush()
        
        assertEquals(OperationResultCode.Success, result)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testForceFlushWithExporterFailure() {
        exporter = FakeSpanExporter(forceFlushReturnValue = { OperationResultCode.Failure })
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        
        val result = processor.forceFlush()
        
        assertEquals(OperationResultCode.Failure, result)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testForceFlushWithException() {
        exporter = FakeSpanExporter(forceFlushReturnValue = { throw RuntimeException("Flush failed") })
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        
        val result = processor.forceFlush()
        
        assertEquals(OperationResultCode.Failure, result)
        assertTrue(errorHandler.hasErrors())
    }

    @Test
    fun testShutdownCallsExporter() {
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        val result = processor.shutdown()
        
        assertEquals(OperationResultCode.Success, result)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testShutdownWithExporterFailure() {
        exporter = FakeSpanExporter(shutdownReturnValue = { OperationResultCode.Failure })
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        
        val result = processor.shutdown()
        
        assertEquals(OperationResultCode.Failure, result)
        assertFalse(errorHandler.hasErrors())
    }

    @Test
    fun testShutdownWithException() {
        exporter = FakeSpanExporter(shutdownReturnValue = { throw RuntimeException("Shutdown failed") })
        val processor = SimpleSpanProcessor(exporter, errorHandler)
        
        val result = processor.shutdown()
        
        assertEquals(OperationResultCode.Failure, result)
        assertTrue(errorHandler.hasErrors())
    }
}