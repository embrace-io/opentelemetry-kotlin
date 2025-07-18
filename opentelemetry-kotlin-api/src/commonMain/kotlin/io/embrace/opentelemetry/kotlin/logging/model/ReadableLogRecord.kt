package io.embrace.opentelemetry.kotlin.logging.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * A read-only representation of a log record.
 *
 * https://opentelemetry.io/docs/specs/otel/logs/sdk/#readablelogrecord
 */
@ExperimentalApi
public interface ReadableLogRecord {

    /**
     * The timestamp in nanoseconds at which the event occurred.
     */
    public val timestamp: Long?

    /**
     * The timestamp in nanoseconds at which the event was entered into the OpenTelemetry API.
     */
    public val observedTimestamp: Long?

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

    /**
     * A map of attributes associated with the log record.
     */
    public val attributes: Map<String, Any>

    /**
     * The span context associated with the log record
     */
    public val spanContext: SpanContext

    /**
     * The resource associated with the log record, if any.
     */
    public val resource: Resource?

    /**
     * The instrumentation scope information associated with the log record, if any.
     */
    public val instrumentationScopeInfo: InstrumentationScopeInfo?
}
