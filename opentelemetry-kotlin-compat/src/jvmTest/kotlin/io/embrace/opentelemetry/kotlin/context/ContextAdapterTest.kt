package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class ContextAdapterTest {

    @Test
    fun testScope() {
        val ctx = ContextAdapter(OtelJavaContext.root())
        val scope = ctx.attach()
        assertNotNull(scope)
        scope.detach()
    }
}
