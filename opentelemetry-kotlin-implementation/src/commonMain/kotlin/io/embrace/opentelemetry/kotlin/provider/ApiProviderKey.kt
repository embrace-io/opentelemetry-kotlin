package io.embrace.opentelemetry.kotlin.provider

/**
 * A key for a tracer/logger provider. The OpenTelemetry spec
 * states that if any of these parameters are different a new instance must be returned.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracerprovider
 * https://opentelemetry.io/docs/specs/otel/logs/api/#loggerprovider
 */
internal data class ApiProviderKey(
    val name: String,
    val version: String? = null,
    val schemaUrl: String? = null,
    val attributes: Map<String, Any> = emptyMap(),
)
