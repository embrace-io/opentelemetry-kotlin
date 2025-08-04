@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo
import io.opentelemetry.sdk.trace.data.SpanData

internal class FakeOtelJavaReadableSpan(
    var otelJavaSpanData: OtelJavaSpanData = FakeOtelJavaSpanData()
) : OtelJavaReadableSpan {

    override fun getSpanContext(): SpanContext = otelJavaSpanData.spanContext

    override fun getParentSpanContext(): SpanContext = otelJavaSpanData.parentSpanContext

    override fun getName(): String = otelJavaSpanData.name

    @Deprecated("Deprecated in Java")
    override fun getInstrumentationLibraryInfo(): InstrumentationLibraryInfo = otelJavaSpanData.instrumentationLibraryInfo

    override fun hasEnded(): Boolean = otelJavaSpanData.hasEnded()

    override fun getLatencyNanos(): Long = otelJavaSpanData.endEpochNanos - otelJavaSpanData.startEpochNanos

    override fun getKind(): SpanKind? = otelJavaSpanData.kind

    override fun getAttributes(): Attributes = otelJavaSpanData.attributes

    override fun <T> getAttribute(key: AttributeKey<T>): T? = otelJavaSpanData.attributes.get(key)

    override fun toSpanData(): SpanData = otelJavaSpanData
}
