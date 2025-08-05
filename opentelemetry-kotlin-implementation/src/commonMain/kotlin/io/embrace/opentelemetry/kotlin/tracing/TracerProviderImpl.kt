package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl

@OptIn(ExperimentalApi::class)
internal class TracerProviderImpl : TracerProvider {

    private val apiProvider = ApiProviderImpl<Tracer> { key ->
        TracerImpl(key)
    }

    override fun getTracer(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): Tracer {
        val key = apiProvider.createKey(attributes, name, version, schemaUrl)
        return apiProvider.getOrCreate(key)
    }
}
