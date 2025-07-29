package io.embrace.opentelemetry.kotlin.k2j.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.k2j.tracing.convertToOtelKotlin
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
internal class StatusDataAdapter(
    impl: OtelJavaStatusData,
) : StatusData {
    override val statusCode: StatusCode = impl.statusCode.convertToOtelKotlin()
    override val description: String? = impl.description
}
