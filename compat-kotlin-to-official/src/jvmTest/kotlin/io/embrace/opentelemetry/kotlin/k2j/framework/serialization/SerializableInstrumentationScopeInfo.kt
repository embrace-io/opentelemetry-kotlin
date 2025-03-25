package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.common.InstrumentationScopeInfo

internal fun InstrumentationScopeInfo.toSerializable() = SerializableInstrumentationScopeInfo(
    name = name,
    version = version.toString(),
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)
