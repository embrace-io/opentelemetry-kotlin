package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.logging.toOtelKotlinSeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.resource.ResourceAdapter
import io.embrace.opentelemetry.kotlin.scope.toOtelKotlinInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class ReadWriteLogRecordAdapter(
    val impl: OtelJavaReadWriteLogRecord
) : ReadWriteLogRecord {

    override var timestamp: Long?
        get() = impl.timestampEpochNanos
        set(value) {
        }

    override var observedTimestamp: Long?
        get() = impl.observedTimestampEpochNanos
        set(value) {
        }

    override var severityNumber: SeverityNumber?
        get() = impl.severity.toOtelKotlinSeverityNumber()
        set(value) {}

    override var severityText: String?
        get() = impl.severityText
        set(value) {
        }

    override var body: String?
        get() = impl.bodyValue?.asString()
        set(value) {
        }

    override fun setBooleanAttribute(key: String, value: Boolean) {
        impl.setAttribute(OtelJavaAttributeKey.booleanKey(key), value)
    }

    override fun setStringAttribute(key: String, value: String) {
        impl.setAttribute(OtelJavaAttributeKey.stringKey(key), value)
    }

    override fun setLongAttribute(key: String, value: Long) {
        impl.setAttribute(OtelJavaAttributeKey.longKey(key), value)
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        impl.setAttribute(OtelJavaAttributeKey.doubleKey(key), value)
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
        impl.setAttribute(OtelJavaAttributeKey.booleanArrayKey(key), value)
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        impl.setAttribute(OtelJavaAttributeKey.stringArrayKey(key), value)
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        impl.setAttribute(OtelJavaAttributeKey.longArrayKey(key), value)
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        impl.setAttribute(OtelJavaAttributeKey.doubleArrayKey(key), value)
    }

    override val attributes: Map<String, Any>
        get() = impl.attributes.toMap()

    override var spanContext: SpanContext
        get() = SpanContextAdapter(impl.spanContext)
        set(value) {}

    override val resource: Resource
        get() = ResourceAdapter(impl.toLogRecordData().resource)

    override val instrumentationScopeInfo: InstrumentationScopeInfo
        get() = impl.instrumentationScopeInfo.toOtelKotlinInstrumentationScopeInfo()
}
