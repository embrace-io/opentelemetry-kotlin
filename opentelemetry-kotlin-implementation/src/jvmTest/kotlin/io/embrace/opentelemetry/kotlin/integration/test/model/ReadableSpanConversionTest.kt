package io.embrace.opentelemetry.kotlin.integration.test.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeReadWriteSpan
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class ReadableSpanConversionTest {

    @Test
    fun `test conversion`() {
        val original = FakeReadWriteSpan(
            attributes = mapOf("foo" to "bar")
        )
        val observed = original.toSerializable()
        assertEquals(original.attributes, observed.attributes)
    }
}
