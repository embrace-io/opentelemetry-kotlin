package io.embrace.opentelemetry.kotlin.k2j.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.k2j.context.ContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyRepository
import io.embrace.opentelemetry.kotlin.k2j.tracing.AttributeContainerImpl
import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import java.util.concurrent.TimeUnit

@ExperimentalApi
internal class LoggerAdapter(
    private val impl: OtelJavaLogger,
    private val contextKeyRepository: ContextKeyRepository = ContextKeyRepository.INSTANCE,
) : Logger {

    override fun log(
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: AttributeContainer.() -> Unit
    ) {
        val builder = impl.logRecordBuilder()

        if (body != null) {
            builder.setBody(body)
        }
        if (timestamp != null) {
            builder.setTimestamp(timestamp, TimeUnit.NANOSECONDS)
        }
        if (observedTimestamp != null) {
            builder.setObservedTimestamp(observedTimestamp, TimeUnit.NANOSECONDS)
        }
        if (context != null) {
            builder.setContext(ContextAdapter(context, contextKeyRepository))
        }
        if (severityNumber != null) {
            builder.setSeverity(severityNumber.toOtelJavaSeverityNumber())
        }
        if (severityText != null) {
            builder.setSeverityText(severityText)
        }

        val container = AttributeContainerImpl()
        attributes(container)
        builder.setAllAttributes(container.otelJavaAttributes())
        builder.emit()
    }
}
