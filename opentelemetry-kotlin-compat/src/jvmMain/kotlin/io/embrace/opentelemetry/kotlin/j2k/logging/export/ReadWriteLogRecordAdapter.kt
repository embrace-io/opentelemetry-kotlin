package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.j2k.bridge.convertToOtelKotlin
import io.embrace.opentelemetry.kotlin.k2j.logging.convertToOtelKotlin
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class ReadWriteLogRecordAdapter(
    private val impl: OtelJavaReadWriteLogRecord
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
        get() = impl.severity.convertToOtelKotlin()
        set(value) {}

    override var severityText: String?
        get() = impl.severityText
        set(value) {
        }

    override var body: String?
        get() = impl.bodyValue?.asString()
        set(value) {
        }

    override val attributes: MutableMap<String, Any>
        get() = impl.attributes.toMap().toMutableMap()

    override var spanContext: SpanContext
        get() = SpanContextAdapter(impl.spanContext)
        set(value) {}

    override val context: Context?
        get() = null

    override val resource: Resource?
        get() = null

    override val instrumentationScopeInfo: InstrumentationScopeInfo
        get() = impl.instrumentationScopeInfo.convertToOtelKotlin()
}
