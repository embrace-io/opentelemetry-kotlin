package io.embrace.opentelemetry.kotlin.provider

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.sync

/**
 * Provides a tracer/logger implementation, creating a new instance via the supplier if nothing
 * matches the key.
 *
 * The OTel spec states that if any of the TracerProvider/LoggerProvider parameters differ,
 * a new Tracer/Logger instance should be returned.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracerprovider
 * https://opentelemetry.io/docs/specs/otel/logs/api/#loggerprovider
 */
@OptIn(ExperimentalApi::class)
@ThreadSafe
internal class ApiProviderImpl<T>(
    val supplier: (key: ApiProviderKey) -> T
) {

    private val map = mutableMapOf<ApiProviderKey, T>()

    fun getOrCreate(key: ApiProviderKey): T = sync(map) {
        map.getOrPut(key) {
            supplier(key)
        }
    }

    fun createKey(
        attributes: MutableAttributeContainer.() -> Unit,
        name: String,
        version: String?,
        schemaUrl: String?
    ): ApiProviderKey {
        val attrs = MutableAttributeContainerImpl().apply {
            attributes()
        }.attributes
        val key = ApiProviderKey(name, version, schemaUrl, attrs)
        return key
    }
}
