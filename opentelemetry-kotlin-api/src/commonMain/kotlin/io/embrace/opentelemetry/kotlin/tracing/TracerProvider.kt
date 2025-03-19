package io.embrace.opentelemetry.kotlin.tracing

/**
 * TracerProvider is a factory for retrieving instances of [Tracer].
 */
public interface TracerProvider {

    /**
     * Returns a [Tracer] matching the given name and (optional) version.
     *
     * The name must document the instrumentation scope: https://opentelemetry.io/docs/specs/otel/glossary/#instrumentation-scope
     */
    public fun getTracer(name: String, version: String? = null): Tracer
}
