package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * TracerProvider is a factory for retrieving instances of [Tracer].
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracerprovider
 */
@ExperimentalApi
@ThreadSafe
public interface TracerProvider {

    /**
     * Returns a [Tracer] matching the given name and (optional) version.
     *
     * The name must document the instrumentation scope: https://opentelemetry.io/docs/specs/otel/glossary/#instrumentation-scope
     */
    @ThreadSafe
    public fun getTracer(name: String, version: String? = null): Tracer
}
