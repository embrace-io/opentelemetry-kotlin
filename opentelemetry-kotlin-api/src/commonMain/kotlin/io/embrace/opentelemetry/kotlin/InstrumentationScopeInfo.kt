package io.embrace.opentelemetry.kotlin

/**
 * Metadata that uniquely identifies the source of telemetry.
 */
public interface InstrumentationScopeInfo {

    public val name: String
    public val version: String?
    public val schemaUrl: String?
    public val attributes: Map<String, Any>
}
