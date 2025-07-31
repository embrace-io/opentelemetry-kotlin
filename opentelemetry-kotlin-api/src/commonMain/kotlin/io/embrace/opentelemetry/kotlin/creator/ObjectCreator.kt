package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Factory that constructs objects that are used within the SDK.
 */
@ExperimentalApi
public interface ObjectCreator {

    /**
     * Factory that constructs [SpanContext] objects.
     */
    public val spanContext: SpanContextCreator
}
