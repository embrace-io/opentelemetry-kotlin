package io.opentelemetry.kotlin.resource

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.ThreadSafe

/**
 * Implementations of this interface hold a 'resource' as described in the OTel specification.
 *
 * https://opentelemetry.io/docs/specs/otel/resource/data-model/
 */
@ExperimentalApi
@ThreadSafe
public interface Resource {

    /**
     * The attributes of the resource.
     */
    public val attributes: Map<String, Any>

    /**
     * A schema URL for this resource, if available.
     */
    public val schemaUrl: String?
}
