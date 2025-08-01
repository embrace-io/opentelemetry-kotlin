package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo

internal fun OtelJavaInstrumentationScopeInfo.toSerializable() = SerializableInstrumentationScopeInfo(
    name = name,
    version = version.toString(),
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)
