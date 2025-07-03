@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")

package io.embrace.opentelemetry.kotlin.fakes.otel.java

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaBody
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSeverity
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext

internal class FakeOtelJavaLogRecordData(
    private val implResource: OtelJavaResource = OtelJavaResource.empty(),
    private val implScopeInfo: OtelJavaInstrumentationScopeInfo = OtelJavaInstrumentationScopeInfo.empty(),
    private val implTimestamp: Long = 0,
    private val implObservedTimestamp: Long = 0,
    private val implSpanContext: OtelJavaSpanContext = OtelJavaSpanContext.getInvalid(),
    private val implSeverity: OtelJavaSeverity = OtelJavaSeverity.WARN,
    private val implSeverityText: String? = "warning",
    private val implBody: String = "Hello, world",
    private val implAttributes: OtelJavaAttributes = OtelJavaAttributes.empty(),
) : OtelJavaLogRecordData {

    override fun getResource(): OtelJavaResource = implResource
    override fun getInstrumentationScopeInfo(): OtelJavaInstrumentationScopeInfo = implScopeInfo
    override fun getTimestampEpochNanos(): Long = implTimestamp
    override fun getObservedTimestampEpochNanos(): Long = implObservedTimestamp
    override fun getSpanContext(): OtelJavaSpanContext = implSpanContext
    override fun getSeverity(): OtelJavaSeverity = implSeverity
    override fun getSeverityText(): String? = implSeverityText
    override fun getBody(): OtelJavaBody = OtelJavaBody.string(implBody)
    override fun getAttributes(): OtelJavaAttributes = implAttributes
    override fun getTotalAttributeCount(): Int = implAttributes.size()
}
