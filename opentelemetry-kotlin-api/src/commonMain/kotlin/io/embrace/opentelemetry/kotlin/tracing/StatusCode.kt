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
public enum class StatusCode {

    /**
     * Default status.
     */
    Unset,

    /**
     * The operation completed successfully.
     */
    Ok,

    /**
     * The operation completed with an error.
     */
    Error
}
