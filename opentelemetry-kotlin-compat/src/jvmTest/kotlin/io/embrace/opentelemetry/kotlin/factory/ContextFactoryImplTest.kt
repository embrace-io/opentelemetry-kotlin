package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.toOtelJavaContext
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
    fun `test current`() {
        assertSame(OtelJavaContext.current(), factory.contextFactory.current().toOtelJavaContext())
    }
}
