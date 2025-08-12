package io.embrace.opentelemetry.kotlin

import java.util.concurrent.CopyOnWriteArrayList

public actual fun <T> threadSafeList(): MutableList<T> = CopyOnWriteArrayList()
