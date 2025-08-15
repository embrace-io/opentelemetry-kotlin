package io.embrace.opentelemetry.kotlin.logging.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * The single source of truth for log record state. This is not exposed to consumers of the API - they
 * are presented with views such as [ReadableLogRecordImpl], depending on which API call they make.
 */
@Suppress("unused", "UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class LogRecordModel : ReadWriteLogRecord {

    override var timestamp: Long?
        get() = throw UnsupportedOperationException()
        set(value) {}

    override var observedTimestamp: Long?
        get() = throw UnsupportedOperationException()
        set(value) {}

    override var severityNumber: SeverityNumber?
        get() = throw UnsupportedOperationException()
        set(value) {}

    override var severityText: String?
        get() = throw UnsupportedOperationException()
        set(value) {}

    override var body: String?
        get() = throw UnsupportedOperationException()
        set(value) {}

    override var spanContext: SpanContext
        get() = throw UnsupportedOperationException()
        set(value) {}

    override val context: Context?
        get() = throw UnsupportedOperationException()

    override val attributes: Map<String, Any>
        get() = throw UnsupportedOperationException()

    override val resource: Resource?
        get() = throw UnsupportedOperationException()

    override val instrumentationScopeInfo: InstrumentationScopeInfo?
        get() = throw UnsupportedOperationException()

    override fun setBooleanAttribute(key: String, value: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun setStringAttribute(key: String, value: String) {
        throw UnsupportedOperationException()
    }

    override fun setLongAttribute(key: String, value: Long) {
        throw UnsupportedOperationException()
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        throw UnsupportedOperationException()
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
        throw UnsupportedOperationException()
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
        throw UnsupportedOperationException()
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
        throw UnsupportedOperationException()
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
        throw UnsupportedOperationException()
    }
}
