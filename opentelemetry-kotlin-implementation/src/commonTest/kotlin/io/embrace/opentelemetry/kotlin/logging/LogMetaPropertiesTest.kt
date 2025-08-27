package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.fakeLogLimitsConfig
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class LogMetaPropertiesTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private val fakeResource = FakeResource()
    private lateinit var logger: LoggerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeLogRecordProcessor

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeLogRecordProcessor()
        logger = LoggerImpl(
            clock,
            processor,
            FakeObjectCreator(),
            key,
            fakeResource,
            fakeLogLimitsConfig
        )
    }

    @Test
    fun `test log instrumentation scope`() {
        logger.log()
        val log = processor.logs.single()
        assertSame(key, log.instrumentationScopeInfo)
    }

    @Test
    fun `test log resource`() {
        logger.log()
        val log = processor.logs.single()
        assertSame(fakeResource, log.resource)
    }
}
