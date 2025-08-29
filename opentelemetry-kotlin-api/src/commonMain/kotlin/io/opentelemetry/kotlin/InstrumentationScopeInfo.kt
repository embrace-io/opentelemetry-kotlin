package io.opentelemetry.kotlin

/**
 * Metadata that uniquely identifies the source of telemetry.
 */
@ExperimentalApi
public interface InstrumentationScopeInfo {

    public val name: String
    public val version: String?
    public val schemaUrl: String?
    public val attributes: Map<String, Any>
}
