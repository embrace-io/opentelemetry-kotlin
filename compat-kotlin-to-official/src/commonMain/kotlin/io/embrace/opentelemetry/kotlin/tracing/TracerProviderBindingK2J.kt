package io.embrace.opentelemetry.kotlin.tracing

internal class TracerProviderBindingK2J(private val entrypoint: CompatEntrypoint) : TracerProvider {

    override fun getTracer(name: String, version: String?): Tracer {
        val tracerProvider = entrypoint.sdk.tracerProvider
        val tracer = when (version) {
            null -> tracerProvider.get(name)
            else -> tracerProvider.get(name, version)
        }
        return TracerBindingK2J(tracer)
    }
}
