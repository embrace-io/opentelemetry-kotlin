package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
internal class FakeStatusData : StatusData {
    override val statusCode: StatusCode
        get() = TODO("Not yet implemented")
    override val description: String?
        get() = TODO("Not yet implemented")
}
