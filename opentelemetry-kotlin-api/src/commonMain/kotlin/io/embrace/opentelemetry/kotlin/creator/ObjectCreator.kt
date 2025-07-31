package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Factory that constructs objects that are used within the SDK.
 */
@ExperimentalApi
public interface ObjectCreator {

    /**
     * Factory that constructs SpanContext objects.
     */
    public val spanContext: SpanContextCreator

    /**
     * Factory that constructs TraceFlags objects.
     */
    public val traceFlags: TraceFlagsCreator

    /**
     * Factory that constructs TraceState objects.
     */
    public val traceState: TraceStateCreator

    /**
     * Factory that constructs Context objects.
     */
    public val context: ContextCreator

    /**
     * Factory that constructs Span objects.
     */
    public val span: SpanCreator
}
