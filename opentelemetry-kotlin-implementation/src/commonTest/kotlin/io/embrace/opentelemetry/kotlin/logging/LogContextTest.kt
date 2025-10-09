package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.factory.createSdkFactory
import io.embrace.opentelemetry.kotlin.logging.export.FakeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.TracerImpl
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.fakeLogLimitsConfig
import io.embrace.opentelemetry.kotlin.tracing.fakeSpanLimitsConfig
import kotlin.math.log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class LogContextTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var logger: LoggerImpl
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeLogRecordProcessor
    private lateinit var sdkFactory: SdkFactory

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeLogRecordProcessor()
        sdkFactory = createSdkFactory()
        logger = LoggerImpl(
            clock,
            processor,
            sdkFactory,
            key,
            FakeResource(),
            fakeLogLimitsConfig
        )
        tracer = TracerImpl(
            clock,
            FakeSpanProcessor(),
            sdkFactory,
            key,
            FakeResource(),
            fakeSpanLimitsConfig
        )
    }

    @Test
    fun testDefaultContext() {
        logger.log()
        val log = processor.logs.single()
        val root = sdkFactory.spanFactory.fromContext(sdkFactory.contextFactory.root()).spanContext
        assertSame(root, log.spanContext)
    }

    @Test
    fun testOverrideContext() {
        val span = tracer.createSpan("span")
        val ctx = sdkFactory.contextFactory.storeSpan(sdkFactory.contextFactory.root(), span)
        logger.log(context = ctx)

        val log = processor.logs.single()
        assertSame(span.spanContext, log.spanContext)
    }

    @Test
    fun testImplicitContext() {
        val span = tracer.createSpan("span")
        val ctx = sdkFactory.contextFactory.storeSpan(sdkFactory.contextFactory.root(), span)
        val scope = ctx.attach()
        logger.log()

        scope.detach()
        logger.log()

        assertEquals(2, processor.logs.size)
        assertSame(span.spanContext, processor.logs[0].spanContext)
        assertSame(sdkFactory.spanContextFactory.invalid, processor.logs[1].spanContext)
    }
}
