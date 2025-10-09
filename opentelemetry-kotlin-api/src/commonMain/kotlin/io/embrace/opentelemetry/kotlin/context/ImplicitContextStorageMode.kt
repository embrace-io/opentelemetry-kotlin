package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Defines the storage mechanism used for the implicit context.
 */
@ExperimentalApi
public enum class ImplicitContextStorageMode {

    /**
     * Implicit context is stored via an in-memory property. Any thread/coroutine can
     * set the context for any others. This is the default storage mechanism.
     */
    GLOBAL
}
