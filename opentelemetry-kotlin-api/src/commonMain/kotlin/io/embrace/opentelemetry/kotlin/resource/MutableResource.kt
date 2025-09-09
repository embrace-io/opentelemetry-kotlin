package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Implementations of this interface hold a 'resource' as described in the OTel specification.
 *
 * https://opentelemetry.io/docs/specs/otel/resource/data-model/
 */
@ExperimentalApi
@ThreadSafe
public interface MutableResource {

    /**
     * The attributes of the resource.
     */
    public val attributes: MutableMap<String, Any>

    /**
     * A schema URL for this resource, if available.
     */
    public var schemaUrl: String?
}
