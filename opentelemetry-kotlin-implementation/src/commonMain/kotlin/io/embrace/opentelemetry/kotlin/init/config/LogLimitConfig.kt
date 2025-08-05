package io.embrace.opentelemetry.kotlin.init.config

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Limits on log data capture.
 */
@ThreadSafe
internal class LogLimitConfig(

    /**
     * Max attribute count.
     */
    val attributeCountLimit: Int,

    /**
     * Max attribute value length.
     */
    val attributeValueLengthLimit: Int,
)
