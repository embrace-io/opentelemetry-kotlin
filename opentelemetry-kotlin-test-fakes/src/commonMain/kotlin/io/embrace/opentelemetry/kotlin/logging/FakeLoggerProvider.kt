package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@OptIn(ExperimentalApi::class)
class FakeLoggerProvider : LoggerProvider {

    val map = mutableMapOf<String, FakeLogger>()

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: AttributeContainer.() -> Unit
    ): Logger = map.getOrPut(name) {
        FakeLogger(name)
    }
}
