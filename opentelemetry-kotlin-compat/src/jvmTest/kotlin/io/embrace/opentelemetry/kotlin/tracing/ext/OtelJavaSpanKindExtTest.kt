package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanKindExtTest {

    @Test
    fun toOtelKotlinSpanKind() {
        val expected = mapOf(
            OtelJavaSpanKind.INTERNAL to SpanKind.INTERNAL,
            OtelJavaSpanKind.CLIENT to SpanKind.CLIENT,
            OtelJavaSpanKind.SERVER to SpanKind.SERVER,
            OtelJavaSpanKind.PRODUCER to SpanKind.PRODUCER,
            OtelJavaSpanKind.CONSUMER to SpanKind.CONSUMER,
        )
        expected.forEach {
            assertEquals(it.key.toOtelKotlinSpanKind(), it.value)
        }
    }
}
