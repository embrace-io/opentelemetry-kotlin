package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TracerProviderConfigImplTest {

    @Test
    fun `test tracing config defaults`() {
        val cfg = TracerProviderConfigImpl().generateTracingConfig()
        assertTrue(cfg.processors.isEmpty())
        assertTrue(cfg.resource.attributes.isEmpty())
        assertNull(cfg.resource.schemaUrl)

        with(cfg.spanLimits) {
            assertEquals(1000, linkCountLimit)
            assertEquals(1000, eventCountLimit)
            assertEquals(1000, attributeCountLimit)
            assertEquals(1000, attributeCountPerLinkLimit)
            assertEquals(1000, attributeCountPerEventLimit)
        }
    }

    @Test
    fun `test tracing config overrides`() {
        val firstProcessor = FakeSpanProcessor()
        val secondProcessor = FakeSpanProcessor()
        val linkCount = 100
        val eventCount = 200
        val attrCount = 300
        val attrCountPerLink = 400
        val attrCountPerEvent = 500
        val schemaUrl = "https://example.com/schema"

        val cfg = TracerProviderConfigImpl().apply {
            addSpanProcessor(firstProcessor)
            addSpanProcessor(secondProcessor)

            resource(schemaUrl) {
                setStringAttribute("key", "value")
            }

            spanLimits {
                linkCountLimit = linkCount
                eventCountLimit = eventCount
                attributeCountLimit = attrCount
                attributeCountPerLinkLimit = attrCountPerLink
                attributeCountPerEventLimit = attrCountPerEvent
            }
        }.generateTracingConfig()

        assertEquals(listOf(firstProcessor, secondProcessor), cfg.processors)
        assertEquals(schemaUrl, cfg.resource.schemaUrl)
        assertEquals(mapOf("key" to "value"), cfg.resource.attributes)

        with(cfg.spanLimits) {
            assertEquals(linkCount, linkCountLimit)
            assertEquals(eventCount, eventCountLimit)
            assertEquals(attrCount, attributeCountLimit)
            assertEquals(attrCountPerLink, attributeCountPerLinkLimit)
            assertEquals(attrCountPerEvent, attributeCountPerEventLimit)
        }
    }
}
