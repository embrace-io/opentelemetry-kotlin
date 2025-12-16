package io.opentelemetry.kotlin.resource

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.resource.MutableResource

@OptIn(ExperimentalApi::class)
class MutableResourceImpl(
    override val attributes: MutableMap<String, Any>,
    override var schemaUrl: String?,
) : MutableResource
