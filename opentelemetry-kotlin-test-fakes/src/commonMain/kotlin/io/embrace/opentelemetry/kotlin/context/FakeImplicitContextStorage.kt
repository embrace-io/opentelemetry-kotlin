package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeImplicitContextStorage : ImplicitContextStorage {

    var context: Context = FakeContext()

    override fun setImplicitContext(context: Context) {
        this.context = context
    }

    override fun implicitContext(): Context = context
}
