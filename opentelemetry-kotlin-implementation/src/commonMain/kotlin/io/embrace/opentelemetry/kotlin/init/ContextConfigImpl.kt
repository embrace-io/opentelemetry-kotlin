package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.ImplicitContextStorageMode

@OptIn(ExperimentalApi::class)
internal class ContextConfigImpl : ContextConfigDsl {
    override var storageMode: ImplicitContextStorageMode = ImplicitContextStorageMode.GLOBAL
}
