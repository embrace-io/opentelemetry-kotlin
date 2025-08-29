package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaContext
import io.opentelemetry.kotlin.context.toOtelJavaContext
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
