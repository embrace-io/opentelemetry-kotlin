package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
fun loadSpanData(resName: String): List<SerializableSpanData> {
    val classLoader = checkNotNull(SerializableSpanData::class.java.classLoader)
    val stream = checkNotNull(classLoader.getResourceAsStream(resName))
    return stream.buffered().use {
        Json.decodeFromStream<List<SerializableSpanData>>(it)
    }
}
