package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordBuilder
import io.embrace.opentelemetry.kotlin.context.toOtelKotlinContext
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.logs.Severity
import io.opentelemetry.context.Context
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class OtelJavaLogRecordBuilderAdapter(private val impl: Logger) :
    OtelJavaLogRecordBuilder {

    private var timestamp: Long? = null
    private var observedTimestamp: Long? = null
    private var context: Context? = null
    private var severity: Severity? = null
    private var severityText: String? = null
    private var body: String? = null
    private val attrs = ConcurrentHashMap<String, String>()

    override fun setTimestamp(timestamp: Long, unit: TimeUnit): OtelJavaLogRecordBuilder {
        this.timestamp = unit.toNanos(timestamp)
        return this
    }

    override fun setTimestamp(instant: Instant): OtelJavaLogRecordBuilder {
        return this
    }

    override fun setObservedTimestamp(timestamp: Long, unit: TimeUnit): OtelJavaLogRecordBuilder {
        this.observedTimestamp = unit.toNanos(timestamp)
        return this
    }

    override fun setObservedTimestamp(instant: Instant): OtelJavaLogRecordBuilder {
        return this
    }

    override fun setContext(context: Context): OtelJavaLogRecordBuilder {
        this.context = context
        return this
    }

    override fun setSeverity(severity: Severity): OtelJavaLogRecordBuilder {
        this.severity = severity
        return this
    }

    override fun setSeverityText(severityText: String): OtelJavaLogRecordBuilder {
        this.severityText = severityText
        return this
    }

    override fun setBody(body: String): OtelJavaLogRecordBuilder {
        this.body = body
        return this
    }

    override fun <T : Any?> setAttribute(
        key: AttributeKey<T>,
        value: T?
    ): OtelJavaLogRecordBuilder {
        attrs[key.key] = value.toString()
        return this
    }

    override fun emit() {
        impl.log(
            body = body,
            timestamp = timestamp,
            observedTimestamp = observedTimestamp,
            context = context?.toOtelKotlinContext(),
            severityNumber = severity?.toOtelKotlinSeverityNumber(),
            severityText = severityText,
        ) {
            attrs.forEach { setStringAttribute(it.key, it.value) }
        }
    }
}
