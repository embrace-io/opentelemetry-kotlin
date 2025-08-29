package io.embrace.opentelemetry.kotlin

/**
 * Gets the type of the given exception or returns null if this cannot be found.
 */
internal expect fun Throwable.exceptionType(): String?
