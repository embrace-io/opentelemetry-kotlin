package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

/**
 * Implementations of this interface hold a 'resource' as described in the OTel specification.
 *
 * https://opentelemetry.io/docs/specs/otel/resource/data-model/
 */
@ExperimentalApi
@ThreadSafe
public typealias Resource = AttributeContainer
