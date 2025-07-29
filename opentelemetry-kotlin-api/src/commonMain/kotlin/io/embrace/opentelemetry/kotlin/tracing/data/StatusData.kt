package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.StatusCode

/**
 * Immutable representation of a Status
 */
@ExperimentalApi
public interface StatusData {

    public val statusCode: StatusCode

    public val description: String?
}
