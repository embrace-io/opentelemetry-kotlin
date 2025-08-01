package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.k2j.context.toOtelJavaContext
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class ContextCreatorImplTest {

    private val creator = createCompatObjectCreator()

    @Test
    fun `test root`() {
        assertSame(OtelJavaContext.root(), creator.context.root().toOtelJavaContext())
    }

    @Test
    fun `test current`() {
        assertSame(OtelJavaContext.current(), creator.context.current().toOtelJavaContext())
    }
}
