package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.toSerializable
import io.opentelemetry.sdk.trace.data.SpanData
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals

internal fun OtelJavaHarness.assertSpans(
    expectedCount: Int,
    goldenFileName: String? = null,
    spanDataAssertions: List<SpanData>.() -> Unit = {},
    spanDataFilter: (SpanData) -> Boolean = { true },
) {
    val observedSpans: List<SpanData> = awaitSpans(expectedCount, spanDataFilter)
    spanDataAssertions(observedSpans)

    if (goldenFileName != null) {
        val observed = observedSpans.map(SpanData::toSerializable)
        val expected = loadSpanData(goldenFileName)
        assertEquals(
            expected,
            observed,
            "Span data does not match expected data. Observed=${Json.encodeToString(observed)}"
        )
    }
}
