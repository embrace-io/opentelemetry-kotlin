package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context

/**
 * Represents a log that can be emitted by a [Logger].
 *
 * https://opentelemetry.io/docs/specs/otel/logs/api/#emit-a-logrecord
 */
@ThreadSafe
@ExperimentalApi
public interface LogRecord : AttributeContainer {

    /**
     * The timestamp at which the event occurred.
     */
    public val timestampNs: Long?

    /**
     * The timestamp at which the event was entered into the OpenTelemetry API.
     */
    public val observedTimestampNs: Long?

    /**
     * The context in which the log was emitted.
     */
    public val context: Context?

    /**
     * The severity of the log.
     */
    public val severityNumber: SeverityNumber?

    /**
     * A string representation of the severity at the point it was captured. This can be distinct from
     * [SeverityNumber] - for example, when capturing logs from a 3rd party library with different severity concepts.
     */
    public val severityText: String?

    /**
     * Contains the body of the log message - i.e. a human-readable string or free-form string data.
     */
    public val body: String?
}
