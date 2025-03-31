package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@OptIn(ExperimentalApi::class)
internal class OtelJavaHarness {

    val tracerProvider: TracerProvider = TODO("Not implemented yet")

    @Suppress("UNUSED_PARAMETER")
    internal fun assertSpans(
        expectedCount: Int,
        goldenFileName: String? = null
    ) {
        TODO("Not implemented yet")
    }
}
