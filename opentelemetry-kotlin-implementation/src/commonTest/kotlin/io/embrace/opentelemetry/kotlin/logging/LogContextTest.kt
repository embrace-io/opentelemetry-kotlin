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
        val root = sdkFactory.span.fromContext(sdkFactory.context.root()).spanContext
        assertSame(root, log.spanContext)
    }

    @Test
    fun testOverrideContext() {
        val span = tracer.createSpan("span")
        val ctx = sdkFactory.context.storeSpan(sdkFactory.context.root(), span)
        logger.log(context = ctx)

        val log = processor.logs.single()
        assertSame(span.spanContext, log.spanContext)
    }
}
