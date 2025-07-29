package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
internal class StatusDataImpl(
    override val statusCode: StatusCode,
    override val description: String?
) : StatusData
