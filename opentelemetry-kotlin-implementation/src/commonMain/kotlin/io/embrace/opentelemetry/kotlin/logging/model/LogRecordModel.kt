package io.embrace.opentelemetry.kotlin.logging.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.ReentrantReadWriteLock
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.init.config.LogLimitConfig
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * The single source of truth for log record state. This is not exposed to consumers of the API - they
 * are presented with views such as [ReadableLogRecordImpl], depending on which API call they make.
 */
@OptIn(ExperimentalApi::class)
internal class LogRecordModel(
    override val resource: Resource,
    override val instrumentationScopeInfo: InstrumentationScopeInfo,
    timestamp: Long,
    observedTimestamp: Long,
    body: String?,
    severityText: String?,
    severityNumber: SeverityNumber?,
    override val spanContext: SpanContext,
    logLimitConfig: LogLimitConfig,
) : ReadWriteLogRecord {

    private val lock = ReentrantReadWriteLock()

    override var timestamp: Long? = timestamp
        get() = readLogRecord { field }
        set(value) {
            writeLogRecord {
                field = value
            }
        }

    override var observedTimestamp: Long? = observedTimestamp
        get() = readLogRecord { field }
        set(value) {
            writeLogRecord {
                field = value
            }
        }

    override var severityNumber: SeverityNumber? = severityNumber
        get() = readLogRecord { field }
        set(value) {
            writeLogRecord {
                field = value
            }
        }

    override var severityText: String? = severityText
        get() = readLogRecord { field }
        set(value) {
            writeLogRecord {
                field = value
            }
        }

    override var body: String? = body
        get() = readLogRecord { field }
        set(value) {
            writeLogRecord {
                field = value
            }
        }

    private val attrs = MutableAttributeContainerImpl(logLimitConfig.attributeCountLimit, mutableMapOf())

    override val attributes: Map<String, Any>
        get() = readLogRecord {
            attrs.attributes.toMap()
        }

    override fun setBooleanAttribute(key: String, value: Boolean) {
        writeLogRecord {
            attrs.setBooleanAttribute(key, value)
        }
    }

    override fun setStringAttribute(key: String, value: String) {
        writeLogRecord {
            attrs.setStringAttribute(key, value)
        }
    }

    override fun setLongAttribute(key: String, value: Long) {
        writeLogRecord {
            attrs.setLongAttribute(key, value)
        }
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        writeLogRecord {
            attrs.setDoubleAttribute(key, value)
        }
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
        writeLogRecord {
            attrs.setBooleanListAttribute(key, value)
        }
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
        writeLogRecord {
            attrs.setStringListAttribute(key, value)
        }
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
        writeLogRecord {
            attrs.setLongListAttribute(key, value)
        }
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
        writeLogRecord {
            attrs.setDoubleListAttribute(key, value)
        }
    }

    private inline fun <T> writeLogRecord(
        crossinline condition: () -> Boolean = { true },
        crossinline action: () -> T
    ) {
        return lock.write {
            if (condition()) {
                action()
            }
        }
    }

    private inline fun <T> readLogRecord(crossinline action: () -> T): T {
        return lock.read {
            action()
        }
    }
}
