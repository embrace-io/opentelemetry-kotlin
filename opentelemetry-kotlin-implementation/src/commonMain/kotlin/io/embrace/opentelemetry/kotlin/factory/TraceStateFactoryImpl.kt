package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.TraceStateImpl
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@ExperimentalApi
internal class TraceStateFactoryImpl : TraceStateFactory {
    override val default: TraceState by lazy { TraceStateImpl.create() }
}
