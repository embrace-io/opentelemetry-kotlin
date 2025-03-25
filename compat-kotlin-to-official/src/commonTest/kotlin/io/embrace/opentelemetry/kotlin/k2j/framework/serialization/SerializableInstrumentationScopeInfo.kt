package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.common.InstrumentationScopeInfo
import kotlinx.serialization.Serializable

internal fun InstrumentationScopeInfo.toSerializable() = SerializableInstrumentationScopeInfo(
    name = name,
    version = version.toString(),
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)

@Serializable
internal data class SerializableInstrumentationScopeInfo(
    val name: String,
    val version: String,
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
