package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
public fun createObjectCreator(): ObjectCreator = ObjectCreatorImpl()
