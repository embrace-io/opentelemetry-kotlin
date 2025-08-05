package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer

@ExperimentalApi
public interface ResourceConfigDsl {
    public fun resource(schemaUrl: String? = null, attributes: MutableAttributeContainer.() -> Unit)
}
