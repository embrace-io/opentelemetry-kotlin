package io.embrace.opentelemetry.kotlin

public actual fun <K, V> threadSafeMap(): MutableMap<K, V> = mutableMapOf()
