package io.embrace.opentelemetry.kotlin

import java.util.concurrent.ConcurrentHashMap

public actual fun <K, V> threadSafeMap(): MutableMap<K, V> = ConcurrentHashMap()
