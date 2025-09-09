package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeResource(
    override val attributes: Map<String, Any> = mapOf("foo" to "bar"),
    override val schemaUrl: String? = "schemaUrl"
) : Resource {

    override fun asNewResource(action: MutableResource.() -> Unit): Resource {
        throw UnsupportedOperationException()
    }
}
