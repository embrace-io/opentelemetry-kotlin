package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalApi::class)
internal class SpanCreatorImplTest {

    private val objectCreatorImpl = createObjectCreator()
    private val creator = objectCreatorImpl.span

    @Test
    fun `invalid span`() {
        assertFalse(creator.invalid.spanContext.isValid)
    }

    @Test
    fun `from span context`() {
        val spanContext = FakeSpanContext(
            traceId = "12345678901234567890123456789012",
            spanId = "1234567890123456",
        )
        assertEquals(spanContext, creator.fromSpanContext(spanContext).spanContext)
    }
}
