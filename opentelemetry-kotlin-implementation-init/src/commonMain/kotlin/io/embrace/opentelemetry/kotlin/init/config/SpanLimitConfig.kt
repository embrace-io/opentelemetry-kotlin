package io.embrace.opentelemetry.kotlin.init.config

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Limits on span data capture.
 */
@ThreadSafe
public class SpanLimitConfig(

    /**
     * Max attribute count.
     */
    public val attributeCountLimit: Int,

    /**
     * Max link count.
     */
    public val linkCountLimit: Int,

    /**
     * Max event count.
     */
    public val eventCountLimit: Int,

    /**
     * Max attributes per event.
     */
    public val attributeCountPerEventLimit: Int,

    /**
     * Max attributes per link.
     */
    public val attributeCountPerLinkLimit: Int,
)

internal const val DEFAULT_LINK_LIMIT: Int = 128
internal const val DEFAULT_EVENT_LIMIT: Int = 128
