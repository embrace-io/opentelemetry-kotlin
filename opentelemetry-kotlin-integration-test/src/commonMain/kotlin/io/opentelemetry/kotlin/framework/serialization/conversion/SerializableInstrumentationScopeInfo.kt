package io.opentelemetry.kotlin.framework.serialization.conversion

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo
import io.opentelemetry.kotlin.framework.serialization.conversion.toSerializable

@OptIn(ExperimentalApi::class)
fun InstrumentationScopeInfo.toSerializable() =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo(
        name = name,
        version = version.toString(),
        schemaUrl = schemaUrl.toString(),
        attributes = attributes.toSerializable(),
    )
