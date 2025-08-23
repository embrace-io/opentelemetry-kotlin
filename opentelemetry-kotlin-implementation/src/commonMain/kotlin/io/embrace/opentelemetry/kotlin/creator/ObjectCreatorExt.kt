package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
public fun createObjectCreator(): ObjectCreator = ObjectCreatorImpl()
