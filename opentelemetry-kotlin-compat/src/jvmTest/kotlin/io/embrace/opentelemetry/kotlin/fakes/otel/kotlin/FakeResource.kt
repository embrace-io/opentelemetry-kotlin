package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
internal class FakeResource(
    override val attributes: Map<String, Any> = mapOf("foo" to "bar"),
    override val schemaUrl: String? = "schemaUrl"
) : Resource
