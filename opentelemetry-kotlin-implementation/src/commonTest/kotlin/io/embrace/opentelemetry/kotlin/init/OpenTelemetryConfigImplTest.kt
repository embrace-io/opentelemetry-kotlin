package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class OpenTelemetryConfigImplTest {

    @Test
    fun testDefaultConfig() {
        val cfg = OpenTelemetryConfigImpl()
        assertTrue(cfg.tracingConfig.generateTracingConfig().processors.isEmpty())
        assertTrue(cfg.loggingConfig.generateLoggingConfig().processors.isEmpty())
        assertNotNull(cfg.clock)
    }

    @Test
    fun testOverrideConfig() {
        val cfg = OpenTelemetryConfigImpl()
        cfg.loggerProvider { addLogRecordProcessor(FakeLogRecordProcessor()) }
        cfg.tracerProvider { addSpanProcessor(FakeSpanProcessor()) }
        assertFalse(cfg.tracingConfig.generateTracingConfig().processors.isEmpty())
        assertFalse(cfg.loggingConfig.generateLoggingConfig().processors.isEmpty())
        assertNotNull(cfg.clock)
    }
}
