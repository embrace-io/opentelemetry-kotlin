package io.embrace.opentelemetry.kotlin

public actual fun <T> threadSafeList(): MutableList<T> {
    throw UnsupportedOperationException()
}
