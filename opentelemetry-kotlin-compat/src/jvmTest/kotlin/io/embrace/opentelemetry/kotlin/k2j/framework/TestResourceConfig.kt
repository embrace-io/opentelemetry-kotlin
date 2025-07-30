package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@OptIn(ExperimentalApi::class)
internal data class TestResourceConfig(
    val schemaUrl: String? = null,
    val attributes: (AttributeContainer.() -> Unit)? = null
)
