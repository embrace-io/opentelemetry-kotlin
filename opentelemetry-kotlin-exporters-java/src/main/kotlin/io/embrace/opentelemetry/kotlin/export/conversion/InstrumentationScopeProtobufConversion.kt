package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.proto.common.v1.InstrumentationScope

@OptIn(ExperimentalApi::class)
fun InstrumentationScopeInfo.toProtobuf(): InstrumentationScope {
    val scope = this
    return InstrumentationScope.newBuilder().apply {
        scope.version?.let(::setVersion)
        setName(scope.name)
        val values = scope.attributes.createKeyValues()
        addAllAttributes(values)
    }.build()
}
