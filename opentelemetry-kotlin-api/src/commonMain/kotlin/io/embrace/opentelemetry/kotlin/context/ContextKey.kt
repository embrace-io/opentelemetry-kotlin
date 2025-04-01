package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * A key that identifies a value in a [Context].
 */
@ExperimentalApi
@ThreadSafe
public interface ContextKey<T> {

    /**
     * The name of the key. This property does NOT uniquely identify the key in a [Context] object - pass the
     * [ContextKey] instead. The OTel specification exposes this property for debugging purposes only.
     */
    @ThreadSafe
    public val name: String
}
