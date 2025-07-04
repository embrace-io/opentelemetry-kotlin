package io.embrace.opentelemetry.kotlin

public class InstrumentationScopeInfoImpl(
    override val name: String,
    override val version: String?,
    override val schemaUrl: String?,
    override val attributes: Map<String, Any>
) : InstrumentationScopeInfo
