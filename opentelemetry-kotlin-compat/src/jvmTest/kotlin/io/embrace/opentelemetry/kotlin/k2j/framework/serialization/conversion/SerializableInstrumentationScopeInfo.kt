package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableInstrumentationScopeInfo

internal fun InstrumentationScopeInfo.toSerializable() = SerializableInstrumentationScopeInfo(
    name = name,
    version = version.toString(),
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toStringMap(),
)
