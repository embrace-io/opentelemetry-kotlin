package io.embrace.opentelemetry.kotlin.logging.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * A read-write representation of a log record.
 *
 * https://opentelemetry.io/docs/specs/otel/logs/sdk/#readablelogrecord
 */
@ExperimentalApi
public interface ReadWriteLogRecord : ReadableLogRecord {

    /**
     * The timestamp in nanoseconds at which the event occurred.
     */
    public override var timestamp: Long?

    /**
     * The timestamp in nanoseconds at which the event was entered into the OpenTelemetry API.
     */
    public override var observedTimestamp: Long?

    /**
     * The severity of the log.
     */
    public override var severityNumber: SeverityNumber?

    /**
     * A string representation of the severity at the point it was captured. This can be distinct from
     * [SeverityNumber] - for example, when capturing logs from a 3rd party library with different severity concepts.
     */
    public override var severityText: String?

    /**
     * Contains the body of the log message - i.e. a human-readable string or free-form string data.
     */
    public override var body: String?

    /**
     * A map of attributes associated with the log record.
     */
    public override val attributes: MutableMap<String, Any>

    /**
     * The span context associated with the log record
     */
    public override var spanContext: SpanContext
}
