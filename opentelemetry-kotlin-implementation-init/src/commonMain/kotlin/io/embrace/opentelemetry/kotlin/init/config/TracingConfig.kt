package io.embrace.opentelemetry.kotlin.init.config

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

/**
 * Configuration for the Tracing API.
 */
@OptIn(ExperimentalApi::class)
@ThreadSafe
public class TracingConfig(

    /**
     * List of processors. These will be executed in the order they are provided.
     */
    public val processors: List<SpanProcessor>,

    /**
     * Limits on span data capture.
     */
    public val spanLimits: SpanLimitConfig,

    /**
     * A resource to append to spans.
     */
    public val resource: Resource
)
