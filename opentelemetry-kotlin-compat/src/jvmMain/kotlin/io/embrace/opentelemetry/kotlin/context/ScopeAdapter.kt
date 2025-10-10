package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaScope

@OptIn(ExperimentalApi::class)
internal class ScopeAdapter(
    private val impl: OtelJavaScope
) : Scope {
    override fun detach() = impl.close()
}
