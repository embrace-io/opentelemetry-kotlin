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
    public val description: String?
) {
    /**
     * Default status.
     */
    @ThreadSafe
    public object Unset : StatusData(StatusCode.Unset, null)

    /**
     * The operation completed successfully.
     */
    @ThreadSafe
    public object Ok : StatusData(StatusCode.Ok, null)

    /**
     * The operation completed with an error. An optional description of the error may be provided.
     */
    @ThreadSafe
    public class Error(description: String?) : StatusData(StatusCode.Error, description)
}
