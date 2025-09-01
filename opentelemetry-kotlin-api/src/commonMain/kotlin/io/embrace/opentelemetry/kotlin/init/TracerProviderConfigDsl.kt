package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

/**
 * Defines configuration for the [io.embrace.opentelemetry.kotlin.tracing.TracerProvider].
 */
@ExperimentalApi
@ConfigDsl
public interface TracerProviderConfigDsl : ResourceConfigDsl {

    /**
     * The span limits configuration for this tracer provider. Processors will be invoked
     * in the order in which they were added.
     */
    public fun spanLimits(action: SpanLimitsConfigDsl.() -> Unit)

    /**
     * Adds a [SpanProcessor] to the tracer provider.
     */
    public fun addSpanProcessor(processor: SpanProcessor)
}
