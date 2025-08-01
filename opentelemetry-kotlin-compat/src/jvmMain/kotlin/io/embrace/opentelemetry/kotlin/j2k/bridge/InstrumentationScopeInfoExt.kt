package io.embrace.opentelemetry.kotlin.j2k.bridge

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap

@OptIn(ExperimentalApi::class)
internal fun InstrumentationScopeInfo.toOtelJavaInstrumentationScopeInfo(): OtelJavaInstrumentationScopeInfo {
    val builder = OtelJavaInstrumentationScopeInfo.builder(name)
    version?.let(builder::setVersion)
    schemaUrl?.let(builder::setSchemaUrl)
    builder.setAttributes(attrsFromMap(attributes))
    return builder.build()
}

@OptIn(ExperimentalApi::class)
internal fun OtelJavaInstrumentationScopeInfo.toOtelKotlinInstrumentationScopeInfo(): InstrumentationScopeInfo =
    InstrumentationScopeInfoImpl(
        name = name,
        version = version,
        schemaUrl = schemaUrl,
        attributes = attributes.toMap()
    )
