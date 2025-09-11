package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class OpenTelemetryCompatExtTest {

    @Test
    fun `toOtelJavaApi returns noop when called on noop instance`() {
        val noopKotlinInstance = createNoopOpenTelemetry()
        val result = noopKotlinInstance.toOtelJavaApi()
        assertSame(OtelJavaOpenTelemetry.noop(), result)
    }
}
