package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaImplicitContextKeyed
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.tracing.model.OtelJavaSpanAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalApi::class)
internal class ContextRetrievalTest {

    private lateinit var impl: OtelJavaContext
    private lateinit var kotlinDecorator: Context
    private lateinit var javaDecorator: OtelJavaContext

    @Before
    fun setUp() {
        impl = OtelJavaContext.current()
        kotlinDecorator = ContextAdapter(impl)
        javaDecorator = OtelJavaContextAdapter(kotlinDecorator)
    }

    @Test
    fun `store and retrieve explicit key from context`() {
        // assert that values are stored via one layer
        val kotlinKey = kotlinDecorator.createKey<String>("kotlin")
        val kotlinValue = "kotlin_value"
        val kotlinCtx = kotlinDecorator.set(kotlinKey, kotlinValue)
        assertEquals(kotlinValue, kotlinCtx.get(kotlinKey))
        assertNull(kotlinDecorator.get(kotlinKey))

        // assert that values are stored via 2 layers
        val javaKey = OtelJavaContextKey.named<Any>("java-key")
        val javaValue = "java_value"
        val javaCtx = javaDecorator.with(javaKey, javaValue)
        assertEquals(javaValue, javaCtx[javaKey])
        assertNull(javaDecorator[javaKey])
    }

    @Test
    fun `store and retrieve implicit key from context`() {
        val implicitValue = "value"
        val javaCtx = javaDecorator.with(ImplicitKeyTestClass(implicitValue))
        assertEquals(implicitValue, javaCtx.get(ImplicitKeyTestClass.KEY))
    }

    @Test
    fun `store and retrieve implicit span from context`() {
        val impl = FakeSpanImpl()
        val kotlinSpan = SpanAdapter(impl, FakeClock(), null, SpanKind.INTERNAL, 0)
        val javaSpan = OtelJavaSpanAdapter(kotlinSpan)

        val ctx = javaDecorator.with(javaSpan)
        val result = ctx.get(FakeSpanImpl.KEY)
        assertEquals(impl, result)
    }

    private class ImplicitKeyTestClass(private val value: String) : OtelJavaImplicitContextKeyed {
        companion object {
            val KEY: OtelJavaContextKey<String> = OtelJavaContextKey.named("test_key")
        }

        override fun storeInContext(context: OtelJavaContext): OtelJavaContext {
            return context.with(KEY, value)
        }
    }

    private class FakeSpanImpl : OtelJavaSpan, OtelJavaImplicitContextKeyed {

        companion object {
            val KEY: OtelJavaContextKey<OtelJavaSpan> = OtelJavaContextKey.named("test_key")
        }

        override fun storeInContext(context: OtelJavaContext): OtelJavaContext {
            return context.with(KEY, this)
        }

        override fun <T : Any?> setAttribute(
            key: OtelJavaAttributeKey<T>,
            value: T?
        ): OtelJavaSpan {
            return this
        }

        override fun addEvent(name: String, attributes: OtelJavaAttributes): OtelJavaSpan {
            return this
        }

        override fun addEvent(
            name: String,
            attributes: OtelJavaAttributes,
            timestamp: Long,
            unit: TimeUnit
        ): OtelJavaSpan {
            return this
        }

        override fun setStatus(statusCode: OtelJavaStatusCode, description: String): OtelJavaSpan {
            return this
        }

        override fun recordException(
            exception: Throwable,
            additionalAttributes: OtelJavaAttributes
        ): OtelJavaSpan {
            return this
        }

        override fun updateName(name: String): OtelJavaSpan {
            return this
        }

        override fun end() {
        }

        override fun end(timestamp: Long, unit: TimeUnit) {
        }

        override fun getSpanContext(): OtelJavaSpanContext = OtelJavaSpanContext.getInvalid()

        override fun isRecording(): Boolean = false
    }
}
