package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.factory.ContextFactory

/**
 * One context can be set globally at a time, irrespective of thread etc.
 */
@OptIn(ExperimentalApi::class)
internal class DefaultImplicitContextStorage(
    contextFactory: ContextFactory,
) : ImplicitContextStorage {

    private val root by lazy { contextFactory.root() }

    override fun setImplicitContext(context: Context) {
        // TODO: implement
    }

    override fun implicitContext(): Context = root
}
