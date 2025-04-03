package io.embrace.opentelemetry.kotlin

/**
 * Represents the status of an operation.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#set-status
 */
@ThreadSafe
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
    public class Error(public val description: String?) : StatusCode()
}
