package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanData
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals

fun compareGoldenFile(
    observed: List<SerializableSpanData>,
    goldenFileName: String
) {
    val expected = loadSpanData(goldenFileName)
    assertEquals(
        expected,
        observed,
        "Span data does not match expected data. Observed=${Json.encodeToString(observed)}"
    )
}
