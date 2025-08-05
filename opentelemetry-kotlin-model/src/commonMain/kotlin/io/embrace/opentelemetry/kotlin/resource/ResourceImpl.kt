package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class ResourceImpl(
    override val attributes: Map<String, Any>,
    override val schemaUrl: String?,
) : Resource
