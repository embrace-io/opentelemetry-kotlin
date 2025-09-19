package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer

@OptIn(ExperimentalApi::class)
class FakeLoggerProvider : LoggerProvider {

    val map = mutableMapOf<String, FakeLogger>()

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ): Logger = map.getOrPut(name) {
        FakeLogger(name)
    }
}
