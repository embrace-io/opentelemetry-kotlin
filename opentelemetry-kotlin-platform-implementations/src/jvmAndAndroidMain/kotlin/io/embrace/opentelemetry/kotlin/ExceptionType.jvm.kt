package io.embrace.opentelemetry.kotlin

public actual fun Throwable.exceptionType(): String? = this::class.qualifiedName
