package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor

/**
 * Defines configuration for the [io.embrace.opentelemetry.kotlin.logging.LoggerProvider].
 */
@ExperimentalApi
@ConfigDsl
public interface LoggerProviderConfigDsl : ResourceConfigDsl {

    /**
     * Adds a [LogRecordProcessor] to the logger provider. Processors will be invoked
     * in the order in which they were added.
     */
    public fun addLogRecordProcessor(processor: LogRecordProcessor)

    /**
     * The log limits configuration for this logger provider.
     */
    public fun logLimits(action: LogLimitsConfigDsl.() -> Unit)
}
