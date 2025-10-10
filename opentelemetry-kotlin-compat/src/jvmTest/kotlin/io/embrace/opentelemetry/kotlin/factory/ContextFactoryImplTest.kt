package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.assertions.assertSpanContextsMatch
import io.embrace.opentelemetry.kotlin.context.toOtelJavaContext
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class ContextFactoryImplTest {

    private val factory = createCompatSdkFactory()

    @Test
    fun `test root`() {
        assertSame(OtelJavaContext.root(), factory.contextFactory.root().toOtelJavaContext())
    }

    @Test
    fun `test store span`() {
        val tracer = createCompatOpenTelemetry().tracerProvider.getTracer("tracer")
        val span = tracer.createSpan("span")
        val contextFactory = factory.contextFactory
        val ctx = contextFactory.storeSpan(contextFactory.root(), span)
        val retrievedSpan = factory.spanFactory.fromContext(ctx)
        assertSpanContextsMatch(span.spanContext, retrievedSpan.spanContext)
    }

    @Test
    fun `test current`() {
        assertSame(OtelJavaContext.current(), factory.contextFactory.implicitContext().toOtelJavaContext())
    }
}
