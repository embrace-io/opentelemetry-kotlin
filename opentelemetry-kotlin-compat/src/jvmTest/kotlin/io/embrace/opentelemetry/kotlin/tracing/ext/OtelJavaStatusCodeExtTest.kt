package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class OtelJavaStatusCodeExtTest {

    @Test
    fun toOtelKotlinStatusData() {
        val map = mapOf(
            OtelJavaStatusCode.UNSET to StatusData.Unset,
            OtelJavaStatusCode.OK to StatusData.Ok,
            OtelJavaStatusCode.ERROR to StatusData.Error(""),
        )
        map.forEach {
            val observed = it.key.toOtelKotlinStatusData("")
            val expected = it.value
            assertEquals(expected.statusCode, observed.statusCode)
            assertEquals(expected.description, observed.description)
        }
    }
}
