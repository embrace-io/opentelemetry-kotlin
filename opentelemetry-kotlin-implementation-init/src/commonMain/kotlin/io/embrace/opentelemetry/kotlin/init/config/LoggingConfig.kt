package io.embrace.opentelemetry.kotlin.init.config

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.resource.Resource

/**
 * Configuration for the Logging API.
 */
@OptIn(ExperimentalApi::class)
@ThreadSafe
public class LoggingConfig(

    /**
     * List of processors. These will be executed in the order they are provided.
     */
    public val processors: List<LogRecordProcessor>,

    /**
     * Limits on log data capture.
     */
    public val logLimits: LogLimitConfig,

    /**
     * A resource to append to spans.
     */
    public val resource: Resource
)
