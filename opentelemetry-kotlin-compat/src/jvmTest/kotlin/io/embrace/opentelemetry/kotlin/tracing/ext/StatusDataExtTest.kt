package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class StatusDataExtTest {

    @Test
    fun toOtelJavaStatusData() {
        val expected = mapOf(
            StatusData.Unset to OtelJavaStatusData.unset(),
            StatusData.Ok to OtelJavaStatusData.ok(),
            StatusData.Error("Whoops") to OtelJavaStatusData.create(
                OtelJavaStatusCode.ERROR,
                "Whoops"
            ),
        )
        expected.forEach {
            assertEquals(it.key.toOtelJavaStatusData(), it.value)
        }
    }
}
