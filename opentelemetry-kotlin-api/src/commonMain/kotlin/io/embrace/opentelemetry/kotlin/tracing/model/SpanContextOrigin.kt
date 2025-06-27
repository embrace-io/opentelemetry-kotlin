package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Indicates whether a [SpanContext] was created locally or received from a remote source.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#isremote
 */
@ThreadSafe
public enum class SpanContextOrigin {
    LOCAL,
    REMOTE
}
