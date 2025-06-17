package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo

internal fun OtelJavaInstrumentationScopeInfo.toSerializable() = SerializableInstrumentationScopeInfo(
    name = name,
    version = version.toString(),
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)
