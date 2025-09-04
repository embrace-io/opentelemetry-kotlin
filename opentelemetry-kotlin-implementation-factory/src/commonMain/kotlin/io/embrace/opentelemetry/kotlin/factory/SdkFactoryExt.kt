package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
public fun createSdkFactory(): SdkFactory = SdkFactoryImpl()
