package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.factory.ContextFactoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class DefaultImplicitContextStorageImplTest {

    private lateinit var factory: ContextFactoryImpl
    private lateinit var storage: DefaultImplicitContextStorage

    @BeforeTest
    fun setUp() {
        factory = ContextFactoryImpl()
        storage = DefaultImplicitContextStorage(factory::root)
    }

    @Test
    fun testStorage() {
        assertSame(factory.root(), storage.implicitContext())
        val other = FakeContext()
        storage.setImplicitContext(other)
        assertSame(other, storage.implicitContext())
    }
}
