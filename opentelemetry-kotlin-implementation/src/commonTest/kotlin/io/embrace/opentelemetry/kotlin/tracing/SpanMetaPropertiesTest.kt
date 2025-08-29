package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanMetaPropertiesTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private val fakeResource = FakeResource()
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
            key,
            fakeResource,
            fakeSpanLimitsConfig,
        )
    }

    @Test
    fun testSpanInstrumentationScope() {
        tracer.createSpan("test").end()
        val scope = processor.endCalls.single().instrumentationScopeInfo
        assertSame(key, scope)
    }

    @Test
    fun testSpanResource() {
        tracer.createSpan("test").end()
        val resource = processor.endCalls.single().resource
        assertSame(fakeResource, resource)
    }
}
