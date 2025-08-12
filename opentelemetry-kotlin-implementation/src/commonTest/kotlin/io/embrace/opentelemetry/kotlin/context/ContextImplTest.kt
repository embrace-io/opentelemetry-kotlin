package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.creator.ContextCreatorImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
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
    fun `test context get value`() {
        assertFailsWith<UnsupportedOperationException> {
            val ctx = creator.root()
            val key = ctx.createKey<String>("my_key")
            ctx.get(key)
        }
    }

    @Test
    fun `test context set value`() {
        assertFailsWith<UnsupportedOperationException> {
            val ctx = creator.root()
            val key = ctx.createKey<String>("my_key")
            ctx.set(key, "")
        }
    }
}
