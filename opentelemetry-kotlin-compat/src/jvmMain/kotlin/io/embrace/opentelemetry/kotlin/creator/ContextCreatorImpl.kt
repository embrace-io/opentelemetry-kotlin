package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.k2j.context.root

@OptIn(ExperimentalApi::class)
internal class ContextCreatorImpl : ContextCreator {

    override fun root(): Context = Context.root()
}
