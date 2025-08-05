package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl

@OptIn(ExperimentalApi::class)
internal class LoggerProviderImpl : LoggerProvider {

    private val apiProvider = ApiProviderImpl<Logger> { key ->
        LoggerImpl(key)
    }

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): Logger {
        val key = apiProvider.createKey(attributes, name, version, schemaUrl)
        return apiProvider.getOrCreate(key)
    }
}
