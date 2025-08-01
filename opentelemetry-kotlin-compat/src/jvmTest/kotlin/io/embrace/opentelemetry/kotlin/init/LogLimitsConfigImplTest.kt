package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class LogLimitsConfigImplTest {

    @Test
    fun `test span limits`() {
        val cfg = LogLimitsConfigImpl()
        cfg.apply {
            attributeCountLimit = 11
            attributeValueLengthLimit = 111
        }
        val impl = cfg.build()
        assertEquals(11, impl.maxNumberOfAttributes)
        assertEquals(111, impl.maxAttributeValueLength)
    }
}
