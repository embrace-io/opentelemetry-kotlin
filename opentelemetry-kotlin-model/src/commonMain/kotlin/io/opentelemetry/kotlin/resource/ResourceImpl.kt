package io.opentelemetry.kotlin.resource

import io.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class ResourceImpl(
    override val attributes: Map<String, Any>,
    override val schemaUrl: String?,
) : Resource
