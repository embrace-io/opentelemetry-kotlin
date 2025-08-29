package io.opentelemetry.kotlin.init

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class LoggerProviderConfigImplTest {

    @Test
    fun `test tracing config defaults`() {
        val cfg = LoggerProviderConfigImpl().generateLoggingConfig()
        assertTrue(cfg.processors.isEmpty())
        assertTrue(cfg.resource.attributes.isEmpty())
        assertNull(cfg.resource.schemaUrl)

        with(cfg.logLimits) {
            assertEquals(128, attributeCountLimit)
            assertEquals(Int.MAX_VALUE, attributeValueLengthLimit)
        }
    }

    @Test
    fun `test tracing config overrides`() {
        val firstProcessor = FakeLogRecordProcessor()
        val secondProcessor = FakeLogRecordProcessor()
        val attrCount = 100
        val attrValueCount = 200
        val schemaUrl = "https://example.com/schema"

        val cfg = LoggerProviderConfigImpl().apply {
            addLogRecordProcessor(firstProcessor)
            addLogRecordProcessor(secondProcessor)

            resource(schemaUrl) {
                setStringAttribute("key", "value")
            }

            logLimits {
                attributeCountLimit = attrCount
                attributeValueLengthLimit = attrValueCount
            }
        }.generateLoggingConfig()

        assertEquals(listOf(firstProcessor, secondProcessor), cfg.processors)
        assertEquals(schemaUrl, cfg.resource.schemaUrl)
        assertEquals(mapOf("key" to "value"), cfg.resource.attributes)

        with(cfg.logLimits) {
            assertEquals(attrCount, attributeCountLimit)
            assertEquals(attrValueCount, attributeValueLengthLimit)
        }
    }
}
