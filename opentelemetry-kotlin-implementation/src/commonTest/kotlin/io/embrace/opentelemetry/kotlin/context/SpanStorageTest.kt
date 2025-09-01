package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.factory.ContextFactory
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.factory.SpanFactory
import io.embrace.opentelemetry.kotlin.factory.createSdkFactory
import io.embrace.opentelemetry.kotlin.tracing.FakeSpan
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanStorageTest {

    private lateinit var sdkFactory: SdkFactory
    private lateinit var spanFactory: SpanFactory
    private lateinit var contextFactory: ContextFactory

    @BeforeTest
    fun setUp() {
        sdkFactory = createSdkFactory()
        spanFactory = sdkFactory.span
        contextFactory = sdkFactory.context
    }

    @Test
    fun testSpanStorage() {
        val span = FakeSpan()
        val root = contextFactory.root()
        val newCtx = contextFactory.storeSpan(root, span)
        val retrievedSpan = spanFactory.fromContext(newCtx)
        assertSame(span, retrievedSpan)
    }

    @Test
    fun testStoringMultipleSpans() {
        val span = FakeSpan("a")
        val otherSpan = FakeSpan("b")
        val root = contextFactory.root()
        val newCtx = contextFactory.storeSpan(root, span)

        val finalCtx = contextFactory.storeSpan(newCtx, otherSpan)
        val retrievedSpan = spanFactory.fromContext(finalCtx)
        assertSame(otherSpan, retrievedSpan)
    }
}
