package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class MutableResourceImpl(
    override val attributes: MutableMap<String, Any>,
    override var schemaUrl: String?,
) : MutableResource
