package io.embrace.opentelemetry.kotlin.framework

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.test.assertEquals

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T> loadGoldenFileData(resName: String): List<T> {
    val classLoader = checkNotNull(T::class.java.classLoader)
    val stream = checkNotNull(classLoader.getResourceAsStream(resName))
    return stream.buffered().use {
        Json.decodeFromStream<List<T>>(it)
    }
}

internal inline fun <reified T> compareGoldenFile(
    observed: List<T>,
    goldenFileName: String
) {
    val expected = loadGoldenFileData<T>(goldenFileName)
    assertEquals(
        expected,
        observed,
        "Observed data does not match expected data. Observed=${Json.encodeToString(observed)}"
    )
}
