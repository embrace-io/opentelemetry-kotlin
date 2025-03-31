package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Clarifies the relationship between spans correlated via parent/child relationship or span links.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spankind
 */
@ThreadSafe
public enum class SpanKind {

    /**
     * A server handling a remote request
     */
    SERVER,

    /**
     * A client handling a remote request
     */
    CLIENT,

    /**
     * Represents the scheduling/initiation of a local/remote operation
     */
    PRODUCER,

    /**
     * Represents the processing of an operation initiated by a [PRODUCER] span
     */
    CONSUMER,

    /**
     * Default value indicating an internal operation within an application.
     */
    INTERNAL,
}
