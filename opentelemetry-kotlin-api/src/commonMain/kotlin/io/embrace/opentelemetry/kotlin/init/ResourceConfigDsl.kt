package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@ExperimentalApi
public interface ResourceConfigDsl {
    public fun resource(attributes: AttributeContainer.() -> Unit, schemaUrl: String? = null)
}
