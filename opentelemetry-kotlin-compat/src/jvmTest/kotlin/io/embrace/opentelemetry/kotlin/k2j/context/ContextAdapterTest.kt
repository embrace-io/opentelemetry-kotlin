package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.Context
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertNull

@OptIn(ExperimentalApi::class)
internal class ContextAdapterTest {

    @Test
    fun `test context`() {
        val repository = ContextKeyRepository()
        val ctx = ContextAdapter(Context.root(), repository)
        val key1 = OtelJavaContextKey.named<String>("foo")
        val key2 = OtelJavaContextKey.named<String>("foo")
        val key3 = OtelJavaContextKey.named<String>("bar")

        assertNull(ctx.get(key1))
        assertNull(ctx.get(key2))
        assertNull(ctx.get(key3))

        val newCtx = ctx.with(key1, "value1")
        assertNotSame(ctx, newCtx)
        assertEquals("value1", newCtx.get(key1))
        assertNull(newCtx.get(key2))
        assertNull(newCtx.get(key3))
    }
}
