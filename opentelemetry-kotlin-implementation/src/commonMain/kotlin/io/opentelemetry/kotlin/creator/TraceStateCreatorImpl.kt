package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.TraceStateImpl
import io.opentelemetry.kotlin.tracing.model.TraceState

@ExperimentalApi
internal class TraceStateCreatorImpl : TraceStateCreator {
    override val default: TraceState = TraceStateImpl.create()
}
