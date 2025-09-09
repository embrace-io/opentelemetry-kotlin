package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalApi::class)
internal class ResourceImplTest {

    @Test
    fun testResourceImpl() {
        val resource = ResourceImpl(mapOf("key" to "value"), "https://example.com/schema")
        val another = resource.asNewResource {
            attributes["foo"] = "value"
            schemaUrl = "https://example.com/another"
        }

        assertEquals("value", resource.attributes["key"])
        assertEquals("value", another.attributes["key"])

        assertNull(resource.attributes["foo"])
        assertEquals("value", another.attributes["foo"])

        assertEquals("https://example.com/schema", resource.schemaUrl)
        assertEquals("https://example.com/another", another.schemaUrl)
    }

    @Test
    fun testEmptyResource() {
        val resource = ResourceImpl(mapOf("key" to "value"), "https://example.com/schema")
        val another = resource.asNewResource {
            attributes.clear()
            schemaUrl = null
        }
        assertEquals(emptyMap(), another.attributes)
        assertNull(another.schemaUrl)
    }

    @Test
    fun testDefensiveCopy() {
        val resource = ResourceImpl(emptyMap(), null)
        lateinit var attrs: MutableMap<String, Any>
        val another = resource.asNewResource {
            attrs = attributes
        }
        attrs["key"] = "value"
        assertEquals(emptyMap(), another.attributes)
    }
}
