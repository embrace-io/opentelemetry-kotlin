package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.creator.ContextCreatorImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class ContextImplTest {

    private lateinit var creator: ContextCreatorImpl

    @BeforeTest
    fun setUp() {
        creator = ContextCreatorImpl()
    }

    @Test
    fun `test context obtain root`() {
        assertSame(creator.root(), creator.root())
    }

    @Test
    fun `test context create context key`() {
        val ctx = creator.root()
        assertNotEquals(ctx.createKey<String>("my_key"), ctx.createKey("my_key"))
    }

    @Test
    fun `test context get absent value`() {
        val ctx = creator.root()
        val key = ctx.createKey<String>("my_key")
        assertNull(ctx.get(key))
    }

    @Test
    fun `test context get present value`() {
        val ctx = creator.root()
        val key = ctx.createKey<String>("my_key")
        val value = "my_value"
        val newCtx = ctx.set(key, value)

        assertNull(ctx.get(key))
        assertEquals(value, newCtx.get(key))
    }

    @Test
    fun `test context multiple values`() {
        val ctx = creator.root()
        val key1 = ctx.createKey<String>("my_key1")
        val key2 = ctx.createKey<String>("my_key2")
        val key3 = ctx.createKey<Int>("my_key3")
        val value1 = "my_value1"
        val value2 = "my_value2"
        val value3 = 42
        val newCtx = ctx.set(key1, value1).set(key2, value2).set(key3, value3)

        assertEquals(value1, newCtx.get(key1))
        assertEquals(value2, newCtx.get(key2))
        assertEquals(value3, newCtx.get(key3))
    }

    @Test
    fun `test context override existing key`() {
        val ctx = creator.root()
        val key = ctx.createKey<String>("my_key")
        val value1 = "my_value1"
        val value2 = "my_value2"

        val newCtx = ctx.set(key, value1).set(key, value2)
        assertEquals(value2, newCtx.get(key))
    }

    @Test
    fun `test context key explicit null`() {
        val ctx = creator.root()
        val key = ctx.createKey<String>("key")
        val newCtx = ctx.set(key, null)
        assertNull(newCtx.get(key))
    }
}
