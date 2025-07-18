package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.resource.Resource

/**
 * Defines configuration for the [io.embrace.opentelemetry.kotlin.logging.LoggerProvider].
 */
@ExperimentalApi
@ConfigDsl
public interface LoggerProviderConfigDsl {

    /**
     * The [Resource] associated with this logger provider.
     */
    public fun resource(action: AttributeContainer.() -> Unit)

    /**
     * Adds a [LogRecordProcessor] to the logger provider.
     */
    public fun addLogRecordProcessor(processor: LogRecordProcessor)

    /**
     * The log limits configuration for this logger provider.
     */
    public fun logLimits(action: LogLimitsConfigDsl.() -> Unit)
}
