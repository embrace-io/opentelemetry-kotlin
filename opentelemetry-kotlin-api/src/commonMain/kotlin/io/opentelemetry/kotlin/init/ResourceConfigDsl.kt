package io.opentelemetry.kotlin.init

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer

@ExperimentalApi
public interface ResourceConfigDsl {
    public fun resource(schemaUrl: String? = null, attributes: MutableAttributeContainer.() -> Unit)
}
