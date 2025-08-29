package io.opentelemetry.kotlin.logging

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.opentelemetry.kotlin.clock.FakeClock
import io.opentelemetry.kotlin.creator.FakeObjectCreator
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.opentelemetry.kotlin.logging.model.SeverityNumber
import io.opentelemetry.kotlin.resource.FakeResource
import io.opentelemetry.kotlin.tracing.fakeLogLimitsConfig
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
    private lateinit var objectCreator: ObjectCreator

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeLogRecordProcessor()
        objectCreator = FakeObjectCreator()
        logger = LoggerImpl(
            clock,
            processor,
            objectCreator,
            key,
            FakeResource(),
            fakeLogLimitsConfig
        )
    }

    @Test
    fun `test minimal log`() {
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
    fun `test log properties`() {
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
