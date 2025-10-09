package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.ImplicitContextStorageMode

/**
 * Defines configuration for Context.
 */
@ExperimentalApi
@ConfigDsl
public interface ContextConfigDsl {

    /**
     * Defines the storage mechanism used for the implicit context.
     */
    public var storageMode: ImplicitContextStorageMode
}
