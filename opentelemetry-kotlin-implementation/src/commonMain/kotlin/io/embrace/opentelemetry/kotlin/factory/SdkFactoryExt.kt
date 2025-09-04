package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
internal fun createSdkFactory(): SdkFactory = SdkFactoryImpl()
