package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.factory.FakeSdkFactory
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.fakeLogLimitsConfig
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalApi::class)
internal class LogSimplePropertiesTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var logger: LoggerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeLogRecordProcessor
    private lateinit var sdkFactory: SdkFactory

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeLogRecordProcessor()
        sdkFactory = FakeSdkFactory()
        logger = LoggerImpl(
            clock,
            processor,
            sdkFactory,
            key,
            FakeResource(),
            fakeLogLimitsConfig
        )
    }

    @Test
    fun testMinimalLog() {
        val now = 5L
        clock.time = now
        logger.log()

        val log = processor.logs.single()
        assertNull(log.body)
        assertEquals(now, log.timestamp)
        assertEquals(now, log.observedTimestamp)
        assertEquals(SeverityNumber.UNKNOWN, log.severityNumber)
        assertNull(log.severityText)
    }

    @Test
    fun testLogProperties() {
        val body = "Hello, World!"
        val severityText = "INFO"
        logger.log(
            body = body,
            timestamp = 2,
            observedTimestamp = 3,
            severityNumber = SeverityNumber.INFO,
            severityText = severityText
        )

        val log = processor.logs.single()
        assertEquals(body, log.body)
        assertEquals(2, log.timestamp)
        assertEquals(3, log.observedTimestamp)
        assertEquals(SeverityNumber.INFO, log.severityNumber)
        assertEquals(severityText, log.severityText)
    }
}
