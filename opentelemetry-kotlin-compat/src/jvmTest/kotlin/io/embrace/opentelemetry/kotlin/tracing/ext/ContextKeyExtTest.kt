package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKeyAdapter
import io.embrace.opentelemetry.kotlin.context.toOtelJavaContextKey
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class ContextKeyExtTest {

    @Test
    fun toOtelJavaContextKey() {
        val impl = OtelJavaContextKey.named<String>("test")
        val adapter = ContextKeyAdapter(impl)
        assertSame(impl, adapter.toOtelJavaContextKey())
    }
}
