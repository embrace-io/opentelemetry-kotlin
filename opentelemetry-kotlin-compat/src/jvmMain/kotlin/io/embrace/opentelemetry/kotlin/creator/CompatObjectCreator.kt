package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class CompatObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator = SpanContextCreatorImpl()
}

/**
 * Creates a factory that constructs objects that work when the SDK is backed by the OTel Java SDK.
 */
@ExperimentalApi
public fun createCompatObjectCreator(): ObjectCreator = CompatObjectCreator()
