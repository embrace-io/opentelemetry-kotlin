package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.provider.ApiProviderKey
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanLinkTest {

    private val fakeSpanContext = FakeSpanContext()
    private val otherFakeSpanContext = FakeSpanContext()
    private val key = ApiProviderKey("key")

    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeSpanProcessor

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        tracer = TracerImpl(
            clock,
            processor,
            FakeObjectCreator(),
            key
        )
    }

    @Test
    fun `test span link`() {
        tracer.createSpan("test").apply {
            addLink(fakeSpanContext)
            addLink(otherFakeSpanContext) {
                setStringAttribute("foo", "bar")
            }
            end()
        }

        val links = retrieveLinks(2)
        assertLinkData(links[0], fakeSpanContext, emptyMap())
        assertLinkData(links[1], otherFakeSpanContext, mapOf("foo" to "bar"))
    }

    @Test
    fun `test two span links with same keys`() {
        tracer.createSpan("test").apply {
            addLink(fakeSpanContext)
            addLink(fakeSpanContext)
            end()
        }
        val links = retrieveLinks(2)
        assertLinkData(links[0], fakeSpanContext, emptyMap())
        assertLinkData(links[1], fakeSpanContext, emptyMap())
    }

    @Test
    fun `test span link after end`() {
        tracer.createSpan("test").apply {
            end()
            addLink(fakeSpanContext)
        }
        retrieveLinks(0)
    }

    private fun retrieveLinks(expected: Int): List<LinkData> {
        val links = processor.endCalls.single().links
        assertEquals(expected, links.size)
        return links
    }

    private fun assertLinkData(
        link: LinkData,
        spanContext: SpanContext,
        attrs: Map<String, Any>
    ) {
        assertEquals(spanContext, link.spanContext)
        assertEquals(attrs, link.attributes)
    }
}
