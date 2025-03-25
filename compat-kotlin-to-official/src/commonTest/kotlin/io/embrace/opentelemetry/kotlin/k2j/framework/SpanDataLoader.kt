package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
internal fun loadSpanData(resName: String): List<SerializableSpanData> {
    val dir = System.getProperty("user.dir")
    val file = File(dir, "src/fixtures/$resName")
    return file.inputStream().buffered().use {
        Json.decodeFromStream<List<SerializableSpanData>>(it)
    }
}
