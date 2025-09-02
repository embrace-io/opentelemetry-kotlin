package io.embrace.opentelemetry.kotlin.logging.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.FakeInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
class FakeReadWriteLogRecord(
    override var timestamp: Long? = null,
    override var observedTimestamp: Long? = null,
    override var severityNumber: SeverityNumber? = SeverityNumber.UNKNOWN,
    override var severityText: String? = null,
    override var body: String? = null,
    override var spanContext: SpanContext = FakeSpanContext(),
    override val attributes: Map<String, Any> = emptyMap(),
    override val resource: Resource = FakeResource(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo =
        FakeInstrumentationScopeInfo(),
) : ReadWriteLogRecord {

    override fun setBooleanAttribute(key: String, value: Boolean) {
    }

    override fun setStringAttribute(key: String, value: String) {
    }

    override fun setLongAttribute(key: String, value: Long) {
    }

    override fun setDoubleAttribute(key: String, value: Double) {
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
    }
}
