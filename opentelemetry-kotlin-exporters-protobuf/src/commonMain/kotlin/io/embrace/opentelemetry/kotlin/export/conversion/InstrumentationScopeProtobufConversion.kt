package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.proto.common.v1.InstrumentationScope

@OptIn(ExperimentalApi::class)
fun InstrumentationScopeInfo.toProtobuf() : InstrumentationScope = InstrumentationScope(
    name = name,
    version = version ?: "",
    attributes = attributes.createKeyValues(),
)
