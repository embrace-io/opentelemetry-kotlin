package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import kotlin.test.BeforeTest
import kotlin.test.Test

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
        )
    }

    @Test
    fun `test minimal log`() {
        logger.log()
        val log = processor.logs.single()
        checkNotNull(log)
    }
}
