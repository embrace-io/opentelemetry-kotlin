package io.opentelemetry.kotlin.provider

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.opentelemetry.kotlin.ThreadSafe
import io.opentelemetry.kotlin.attributes.DEFAULT_ATTRIBUTE_LIMIT
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.opentelemetry.kotlin.sync

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
    val supplier: (key: InstrumentationScopeInfo) -> T
) {

    private val map = mutableMapOf<InstrumentationScopeInfo, T>()

    fun getOrCreate(key: InstrumentationScopeInfo): T = sync(map) {
        map.getOrPut(key) {
            supplier(key)
        }
    }

    fun createInstrumentationScopeInfo(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): InstrumentationScopeInfo {
        val attrs = MutableAttributeContainerImpl(DEFAULT_ATTRIBUTE_LIMIT).apply {
            attributes()
        }.attributes
        return InstrumentationScopeInfoImpl(name, version, schemaUrl, attrs)
    }
}
