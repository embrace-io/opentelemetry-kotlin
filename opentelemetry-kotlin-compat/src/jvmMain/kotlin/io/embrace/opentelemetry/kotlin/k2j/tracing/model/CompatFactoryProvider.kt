package io.embrace.opentelemetry.kotlin.k2j.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.FactoryProvider
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextFactory

@OptIn(ExperimentalApi::class)
internal class CompatFactoryProvider : FactoryProvider {
    override val spanContextFactory: SpanContextFactory = SpanContextFactoryImpl()
}

@ExperimentalApi
public fun createCompatFactoryProvider(): FactoryProvider = CompatFactoryProvider()
