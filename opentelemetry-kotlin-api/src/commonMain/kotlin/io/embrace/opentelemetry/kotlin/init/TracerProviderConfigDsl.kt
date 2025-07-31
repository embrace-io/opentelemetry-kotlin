package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

/**
 * Defines configuration for the [io.embrace.opentelemetry.kotlin.tracing.TracerProvider].
 */
@ExperimentalApi
@ConfigDsl
public interface TracerProviderConfigDsl {

    /**
     * The [Resource] associated with this tracer provider.
     */
    public fun resource(attributes: AttributeContainer.() -> Unit, schemaUrl: String? = null)

    /**
     * The span limits configuration for this tracer provider.
     */
    public fun spanLimits(action: SpanLimitsConfigDsl.() -> Unit)

    /**
     * Adds a [SpanProcessor] to the tracer provider.
     */
    public fun addSpanProcessor(processor: SpanProcessor)
}
