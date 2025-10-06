package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.DEFAULT_ATTRIBUTE_LIMIT
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TracerProviderConfigImplTest {

    @Test
    fun testDefaultTracingConfig() {
        val cfg = TracerProviderConfigImpl().generateTracingConfig()
        assertTrue(cfg.processors.isEmpty())
        assertTrue(cfg.resource.attributes.isEmpty())
        assertNull(cfg.resource.schemaUrl)

        with(cfg.spanLimits) {
            assertEquals(128, linkCountLimit)
            assertEquals(128, eventCountLimit)
            assertEquals(128, attributeCountLimit)
            assertEquals(128, attributeCountPerLinkLimit)
            assertEquals(128, attributeCountPerEventLimit)
        }
    }

    @Test
    fun testOverrideTracingConfig() {
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

    @Test
    fun testResourceOverride() {
        val cfg = TracerProviderConfigImpl().apply {
            resource(mapOf("extra" to true))
        }.generateTracingConfig()
        assertEquals(mapOf("extra" to true), cfg.resource.attributes)
    }

    @Test
    fun testSimpleResourceConfig() {
        val cfg = TracerProviderConfigImpl().apply {
            resource(mapOf("key" to "value"))
        }.generateTracingConfig()
        assertEquals(mapOf("key" to "value"), cfg.resource.attributes)
    }

    @Test
    fun testResourceLimit() {
        val attrs = (0..DEFAULT_ATTRIBUTE_LIMIT + 2).associate {
            "key$it" to "value$it"
        }
        val cfg = TracerProviderConfigImpl().apply {
            resource(attrs)
        }.generateTracingConfig()
        assertEquals(DEFAULT_ATTRIBUTE_LIMIT, cfg.resource.attributes.size)
    }
}
