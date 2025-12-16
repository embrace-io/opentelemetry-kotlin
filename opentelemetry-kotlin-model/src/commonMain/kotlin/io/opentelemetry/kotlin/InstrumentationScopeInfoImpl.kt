package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfo

@OptIn(ExperimentalApi::class)
data class InstrumentationScopeInfoImpl(
    override val name: String,
    override val version: String?,
    override val schemaUrl: String?,
    override val attributes: Map<String, Any>
) : InstrumentationScopeInfo
