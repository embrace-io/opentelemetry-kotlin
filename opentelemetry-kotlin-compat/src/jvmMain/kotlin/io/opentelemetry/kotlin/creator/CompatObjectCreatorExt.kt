package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

/**
 * Creates a factory that constructs objects that work when the SDK is backed by the OTel Java SDK.
 */
@ExperimentalApi
public fun createCompatObjectCreator(): ObjectCreator = CompatObjectCreator()
