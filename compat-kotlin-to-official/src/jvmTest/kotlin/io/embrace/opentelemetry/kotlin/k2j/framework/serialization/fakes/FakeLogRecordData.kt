@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.fakes

import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.logs.Severity
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.sdk.common.InstrumentationScopeInfo
import io.opentelemetry.sdk.logs.data.Body
import io.opentelemetry.sdk.logs.data.LogRecordData
import io.opentelemetry.sdk.resources.Resource

internal class FakeLogRecordData(
    private val implResource: Resource = Resource.empty(),
    private val implScopeInfo: InstrumentationScopeInfo = InstrumentationScopeInfo.empty(),
    private val implTimestamp: Long = 0,
    private val implObservedTimestamp: Long = 0,
    private val implSpanContext: SpanContext = SpanContext.getInvalid(),
    private val implSeverity: Severity = Severity.WARN,
    private val implSeverityText: String? = "warning",
    private val implBody: String = "Hello, world",
    private val implAttributes: Attributes = Attributes.empty(),
) : LogRecordData {

    override fun getResource(): Resource = implResource
    override fun getInstrumentationScopeInfo(): InstrumentationScopeInfo = implScopeInfo
    override fun getTimestampEpochNanos(): Long = implTimestamp
    override fun getObservedTimestampEpochNanos(): Long = implObservedTimestamp
    override fun getSpanContext(): SpanContext = implSpanContext
    override fun getSeverity(): Severity = implSeverity
    override fun getSeverityText(): String? = implSeverityText
    override fun getBody(): Body = Body.string(implBody)
    override fun getAttributes(): Attributes = implAttributes
    override fun getTotalAttributeCount(): Int = implAttributes.size()
}
