package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanLimitsConfigImplTest {

    @Test
    fun `test span limits`() {
        val cfg = SpanLimitsConfigImpl()
        cfg.apply {
            eventCountLimit = 1
            attributeCountLimit = 2
            linkCountLimit = 3
            attributeCountPerLinkLimit = 4
            attributeCountPerEventLimit = 5
        }
        val impl = cfg.build()
        assertEquals(1, impl.maxNumberOfEvents)
        assertEquals(2, impl.maxNumberOfAttributes)
        assertEquals(3, impl.maxNumberOfLinks)
        assertEquals(4, impl.maxNumberOfAttributesPerLink)
        assertEquals(5, impl.maxNumberOfAttributesPerEvent)
    }
}
