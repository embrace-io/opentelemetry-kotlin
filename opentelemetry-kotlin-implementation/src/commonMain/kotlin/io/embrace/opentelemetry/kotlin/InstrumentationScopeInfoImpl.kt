package io.embrace.opentelemetry.kotlin

@OptIn(ExperimentalApi::class)
public class InstrumentationScopeInfoImpl(
    override val name: String,
    override val version: String?,
    override val schemaUrl: String?,
    override val attributes: Map<String, Any>
) : InstrumentationScopeInfo
