package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.creator.ObjectCreatorImpl
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.TracerImpl
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class LogContextTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var logger: LoggerImpl
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeLogRecordProcessor
    private lateinit var objectCreator: ObjectCreator

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeLogRecordProcessor()
        objectCreator = ObjectCreatorImpl()
        logger = LoggerImpl(
            clock,
            processor,
            objectCreator,
            key,
            FakeResource(),
        )
        tracer = TracerImpl(
            clock,
            FakeSpanProcessor(),
            objectCreator,
            key,
            FakeResource(),
        )
    }

    @Test
    fun `test default context`() {
        logger.log()
        val log = processor.logs.single()
        val root = objectCreator.span.fromContext(objectCreator.context.root()).spanContext
        assertSame(root, log.spanContext)
    }

    @Test
    fun `test non-default context`() {
        val span = tracer.createSpan("span")
        val ctx = objectCreator.context.storeSpan(objectCreator.context.root(), span)
        logger.log(context = ctx)

        val log = processor.logs.single()
        assertSame(span.spanContext, log.spanContext)
    }
}
