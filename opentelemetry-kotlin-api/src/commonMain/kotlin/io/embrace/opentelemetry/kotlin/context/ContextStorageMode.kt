package io.embrace.opentelemetry.kotlin.context

/**
 * The approach used for storing the current context.
 */
public enum class ContextStorageMode {

    /**
     * The current context will be stored in the local thread.
     */
    THREAD_LOCAL,

    /**
     * The current context will be stored in a coroutine context.
     */
    COROUTINE_CONTEXT,
}
