package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.TraceStateImpl
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@ExperimentalApi
internal class TraceStateCreatorImpl : TraceStateCreator {
    override val default: TraceState = TraceStateImpl.create()
}
