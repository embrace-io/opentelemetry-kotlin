package io.embrace.opentelemetry.kotlin

internal actual fun Throwable.exceptionType(): String? = this::class.simpleName
