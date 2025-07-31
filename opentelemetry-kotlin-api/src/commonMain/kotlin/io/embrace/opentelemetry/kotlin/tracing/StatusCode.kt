package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Represents the status of an operation.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#set-status
 */
@ThreadSafe
@ExperimentalApi
public sealed class StatusCode {

    /**
     * Default status.
     */
    @ThreadSafe
    public object Unset : StatusCode()

    /**
     * The operation completed successfully.
     */
    @ThreadSafe
    public object Ok : StatusCode()

    /**
     * The operation completed with an error. An optional description of the error may be provided.
     */
    @ThreadSafe
    public object Error : StatusCode()
}
