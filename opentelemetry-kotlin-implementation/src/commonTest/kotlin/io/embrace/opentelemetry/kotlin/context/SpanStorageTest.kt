package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.creator.ContextCreator
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.creator.SpanCreator
import io.embrace.opentelemetry.kotlin.creator.createObjectCreator
import io.embrace.opentelemetry.kotlin.tracing.FakeSpan
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanStorageTest {

    private lateinit var objectCreator: ObjectCreator
    private lateinit var spanCreator: SpanCreator
    private lateinit var contextCreator: ContextCreator

    @BeforeTest
    fun setUp() {
        objectCreator = createObjectCreator()
        spanCreator = objectCreator.span
        contextCreator = objectCreator.context
    }

    @Test
    fun `test span storage and retrieval`() {
        val span = FakeSpan()
        val root = contextCreator.root()
        val newCtx = contextCreator.storeSpan(root, span)
        val retrievedSpan = spanCreator.fromContext(newCtx)
        assertSame(span, retrievedSpan)
    }

    @Test
    fun `test storing multiple spans`() {
        val span = FakeSpan("a")
        val otherSpan = FakeSpan("b")
        val root = contextCreator.root()
        val newCtx = contextCreator.storeSpan(root, span)

        val finalCtx = contextCreator.storeSpan(newCtx, otherSpan)
        val retrievedSpan = spanCreator.fromContext(finalCtx)
        assertSame(otherSpan, retrievedSpan)
    }
}
