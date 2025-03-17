package io.embrace.opentelemetry.kotlin.fibonacci

public fun generateFibi(): Sequence<Int> = sequence {
    var a = firstElement
    yield(a)
    var b = secondElement
    yield(b)
    while (true) {
        val c = a + b
        yield(c)
        a = b
        b = c
    }
}

public expect val firstElement: Int
public expect val secondElement: Int
