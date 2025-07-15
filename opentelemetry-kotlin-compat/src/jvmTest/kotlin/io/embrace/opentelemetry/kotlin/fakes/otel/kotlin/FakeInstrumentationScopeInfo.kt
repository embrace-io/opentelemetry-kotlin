package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo

@OptIn(ExperimentalApi::class)
internal class FakeInstrumentationScopeInfo(
    override val name: String = "name",
    override val version: String? = "version",
    override val schemaUrl: String? = "schemaUrl",
    override val attributes: Map<String, Any> = mapOf("key" to "value")
) : InstrumentationScopeInfo
