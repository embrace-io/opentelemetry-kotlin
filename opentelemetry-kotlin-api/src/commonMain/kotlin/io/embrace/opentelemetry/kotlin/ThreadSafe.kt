package io.embrace.opentelemetry.kotlin

/**
 * Marks an API whose functions are all thread-safe & can be called concurrently.
 *
 * This annotation is for documentation purposes and does not have any effect on the code.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#concurrency
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
public annotation class ThreadSafe
