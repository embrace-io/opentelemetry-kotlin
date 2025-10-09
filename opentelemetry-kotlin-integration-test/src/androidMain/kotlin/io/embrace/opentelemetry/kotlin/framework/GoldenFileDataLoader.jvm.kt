package io.embrace.opentelemetry.kotlin.framework

internal actual fun loadTestFixture(fixtureName: String): String {
    val stream = checkNotNull(ClassLoader.getSystemResourceAsStream(fixtureName)) {
        "Resource '$fixtureName' not found"
    }
    return stream.bufferedReader().readText()
}
