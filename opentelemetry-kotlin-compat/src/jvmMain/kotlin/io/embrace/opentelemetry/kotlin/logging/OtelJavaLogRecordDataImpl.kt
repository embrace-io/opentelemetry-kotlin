@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSeverity
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.logs.Severity
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.sdk.common.InstrumentationScopeInfo
import io.opentelemetry.sdk.logs.data.Body
import io.opentelemetry.sdk.resources.Resource

/**
 * Implementation of [io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData] that we can construct new instances of. Required for
 * backwards compatibility with opentelemetry-java exporters.
 */
internal class OtelJavaLogRecordDataImpl(
    private val resourceImpl: OtelJavaResource,
    private val scopeImpl: OtelJavaInstrumentationScopeInfo,
    private val timestampNanos: Long,
    private val observedTimestampNanos: Long,
    private val spanContextImpl: OtelJavaSpanContext,
    private val severityImpl: OtelJavaSeverity,
    private val severityTextImpl: String?,
    private val bodyImpl: Body,
    private val attributesImpl: OtelJavaAttributes,
) : OtelJavaLogRecordData {

    override fun getResource(): Resource = resourceImpl
    override fun getInstrumentationScopeInfo(): InstrumentationScopeInfo = scopeImpl
    override fun getTimestampEpochNanos(): Long = timestampNanos
    override fun getObservedTimestampEpochNanos(): Long = observedTimestampNanos
    override fun getSpanContext(): SpanContext = spanContextImpl
    override fun getSeverity(): Severity = severityImpl
    override fun getSeverityText(): String? = severityTextImpl

    @Deprecated("Deprecated in Java")
    override fun getBody(): Body = bodyImpl

    override fun getAttributes(): Attributes = attributesImpl
    override fun getTotalAttributeCount(): Int = attributesImpl.size()
}
