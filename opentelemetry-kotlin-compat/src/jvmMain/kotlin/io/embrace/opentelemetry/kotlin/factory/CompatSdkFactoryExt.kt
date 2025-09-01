package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Creates a factory that constructs objects that work when the SDK is backed by the OTel Java SDK.
 */
@ExperimentalApi
public fun createCompatSdkFactory(): SdkFactory = CompatSdkFactory()
