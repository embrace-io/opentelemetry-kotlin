package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber

/**
 * Class that emits [io.embrace.opentelemetry.kotlin.logging.model.LogRecord] objects.
 *
 * https://opentelemetry.io/docs/specs/otel/logs/api/#logger
 */
@ExperimentalApi
@ThreadSafe
public interface Logger {

    /**
     * Emits a [io.embrace.opentelemetry.kotlin.logging.model.LogRecord] with the given optional parameters:
     *
     * - [body] - the body of the log message
     * - [timestampNs] - the timestamp at which the event occurred
     * - [observedTimestampNs] - the timestamp at which the event was entered into the OpenTelemetry API
     * - [context] - the context in which the log was emitted
     * - [severityNumber] - the severity of the log
     * - [severityText] - a string representation of the severity at the point it was captured
     * - [attributes] - additional attributes to associate with the log
     */
    public fun log(
        body: String? = null,
        timestampNs: Long? = null,
        observedTimestampNs: Long? = null,
        context: Context? = null,
        severityNumber: SeverityNumber? = null,
        severityText: String? = null,
        attributes: AttributeContainer.() -> Unit = {},
    )
}
