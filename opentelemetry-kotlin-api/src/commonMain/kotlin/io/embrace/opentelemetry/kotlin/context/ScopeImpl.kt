package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ScopeImpl(
    private val previousContext: Context,
    private val currentContext: Context,
    private val storage: ImplicitContextStorage,
) : Scope {

    override fun detach() {
        if (storage.implicitContext() == currentContext) {
            storage.setImplicitContext(previousContext)
        }
    }
}
