package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextImpl

@OptIn(ExperimentalApi::class)
internal class ContextCreatorImpl : ContextCreator {

    private val root = ContextImpl()

    override fun root(): Context {
        return root
    }
}
