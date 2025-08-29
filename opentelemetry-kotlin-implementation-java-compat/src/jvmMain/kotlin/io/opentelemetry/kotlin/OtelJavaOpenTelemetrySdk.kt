package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.aliases.OtelJavaContextPropagators
import io.opentelemetry.kotlin.aliases.OtelJavaLoggerProvider
import io.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.opentelemetry.kotlin.aliases.OtelJavaTracerProvider

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
