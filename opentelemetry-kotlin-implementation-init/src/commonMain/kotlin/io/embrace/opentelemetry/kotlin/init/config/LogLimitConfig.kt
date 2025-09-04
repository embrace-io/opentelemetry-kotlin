package io.embrace.opentelemetry.kotlin.init.config

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Limits on log data capture.
 */
@ThreadSafe
public class LogLimitConfig(

    /**
     * Max attribute count.
     */
    public val attributeCountLimit: Int,

    /**
     * Max attribute value length.
     */
    public val attributeValueLengthLimit: Int,
)
