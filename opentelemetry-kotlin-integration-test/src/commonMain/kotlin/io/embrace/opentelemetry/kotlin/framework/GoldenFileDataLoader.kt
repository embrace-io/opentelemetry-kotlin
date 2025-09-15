package io.embrace.opentelemetry.kotlin.framework

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal expect fun loadTestFixture(fixtureName: String): String

internal inline fun <reified T> compareGoldenFile(
    observed: List<T>,
    goldenFileName: String
) {
    val fixture = loadTestFixture(goldenFileName)
    val expected = Json.decodeFromString<List<T>>(fixture)
    if (expected != observed) {
        error(
            "Observed data does not match expected data. Observed=${
                Json.encodeToString(observed)
            }"
        )
    }
}
