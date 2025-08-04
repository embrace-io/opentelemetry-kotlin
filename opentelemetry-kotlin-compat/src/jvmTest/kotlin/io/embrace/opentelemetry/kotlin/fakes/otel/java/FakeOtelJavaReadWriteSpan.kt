@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.sdk.common.InstrumentationScopeInfo
import java.util.concurrent.TimeUnit

/**
 * Only methods on the [readableSpan] interface are valid to call on this fake
 */
internal class FakeOtelJavaReadWriteSpan(
    val readableSpan: OtelJavaReadableSpan = FakeOtelJavaReadableSpan()
) : OtelJavaReadWriteSpan, OtelJavaReadableSpan by readableSpan {

    override fun getInstrumentationScopeInfo(): InstrumentationScopeInfo {
        return readableSpan.instrumentationScopeInfo
    }

    override fun getAttributes(): Attributes {
        return readableSpan.attributes
    }

    override fun <T : Any?> setAttribute(
        key: AttributeKey<T?>,
        value: T?
    ): Span? {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, attributes: Attributes): Span? {
        TODO("Not yet implemented")
    }

    override fun addEvent(
        name: String,
        attributes: Attributes,
        timestamp: Long,
        unit: TimeUnit
    ): Span? {
        TODO("Not yet implemented")
    }

    override fun setStatus(
        statusCode: StatusCode,
        description: String
    ): Span? {
        TODO("Not yet implemented")
    }

    override fun recordException(
        exception: Throwable,
        additionalAttributes: Attributes
    ): Span? {
        TODO("Not yet implemented")
    }

    override fun updateName(name: String): Span? {
        TODO("Not yet implemented")
    }

    override fun end() {
        TODO("Not yet implemented")
    }

    override fun end(timestamp: Long, unit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun isRecording(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLatencyNanos(): Long {
        TODO("Not yet implemented")
    }
}
