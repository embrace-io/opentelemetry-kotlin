@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")

package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationLibraryInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind

internal class FakeOtelJavaReadableSpan(
    var otelJavaSpanData: OtelJavaSpanData = FakeOtelJavaSpanData()
) : OtelJavaReadableSpan {

    override fun getSpanContext(): OtelJavaSpanContext = otelJavaSpanData.spanContext

    override fun getParentSpanContext(): OtelJavaSpanContext = otelJavaSpanData.parentSpanContext

    override fun getName(): String = otelJavaSpanData.name

    @Deprecated("Deprecated in Java")
    override fun getInstrumentationLibraryInfo(): OtelJavaInstrumentationLibraryInfo = otelJavaSpanData.instrumentationLibraryInfo

    override fun hasEnded(): Boolean = otelJavaSpanData.hasEnded()

    override fun getLatencyNanos(): Long = otelJavaSpanData.endEpochNanos - otelJavaSpanData.startEpochNanos

    override fun getKind(): OtelJavaSpanKind? = otelJavaSpanData.kind

    override fun getAttributes(): OtelJavaAttributes = otelJavaSpanData.attributes

    override fun <T> getAttribute(key: OtelJavaAttributeKey<T>): T? = otelJavaSpanData.attributes.get(key)

    override fun toSpanData(): OtelJavaSpanData = otelJavaSpanData
}
