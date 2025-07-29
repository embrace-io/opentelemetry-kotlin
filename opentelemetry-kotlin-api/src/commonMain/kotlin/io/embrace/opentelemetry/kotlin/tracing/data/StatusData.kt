package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

/**
 * Immutable representation of a Status
 */
@ExperimentalApi
public sealed class StatusData(
    public val statusCode: StatusCode,
    public val description: String
) {
    /**
     * Default status.
     */
    @ThreadSafe
    public object Unset : StatusData(StatusCode.Unset, "")

    /**
     * The operation completed successfully.
     */
    @ThreadSafe
    public object Ok : StatusData(StatusCode.Ok, "")

    /**
     * The operation completed with an error.
     */
    @ThreadSafe
    public object Error : StatusData(StatusCode.Error, "")

    /**
     * Create a custom status with a status code and description.
     */
    @ThreadSafe
    public class Custom(statusCode: StatusCode, description: String) : StatusData(statusCode, description)
}
