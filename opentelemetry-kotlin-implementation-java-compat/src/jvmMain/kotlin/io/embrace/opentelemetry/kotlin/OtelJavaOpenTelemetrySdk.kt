package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextPropagators
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLoggerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerProvider

@Suppress("unused")
@OptIn(ExperimentalApi::class)
internal class OtelJavaOpenTelemetrySdk(
    private val impl: OpenTelemetry
) : OtelJavaOpenTelemetry {

    override fun getTracerProvider(): OtelJavaTracerProvider {
        throw UnsupportedOperationException()
    }

    override fun getLogsBridge(): OtelJavaLoggerProvider {
        throw UnsupportedOperationException()
    }

    override fun getPropagators(): OtelJavaContextPropagators {
        throw UnsupportedOperationException()
    }
}
