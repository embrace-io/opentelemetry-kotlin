package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class OtelJavaOpenTelemetryExtTest {

    @Test
    fun testToOtelKotlinApi() {
        val otel = OtelJavaOpenTelemetry.noop().toOtelKotlinApi()
        checkNotNull(otel)
    }
}
