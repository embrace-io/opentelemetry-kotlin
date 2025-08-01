package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class NonRecordingSpanTest {

    @Test
    fun `test non recording span`() {
        val span = NonRecordingSpan(
            FakeSpanContext(),
            FakeSpanContext(),
        )
        assertTrue(span.links.isEmpty())
        assertTrue(span.attributes().isEmpty())
        assertTrue(span.events.isEmpty())
        assertFalse(span.isRecording())
    }
}
