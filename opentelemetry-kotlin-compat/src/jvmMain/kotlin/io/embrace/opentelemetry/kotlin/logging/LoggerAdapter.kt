package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.attributes.CompatMutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.OtelJavaContextAdapter
import io.embrace.opentelemetry.kotlin.context.OtelJavaContextKeyRepository
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import java.util.concurrent.TimeUnit

@ExperimentalApi
internal class LoggerAdapter(
    private val impl: OtelJavaLogger,
    private val contextKeyRepository: OtelJavaContextKeyRepository = OtelJavaContextKeyRepository.INSTANCE,
) : Logger {

    override fun log(
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
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
            builder.setContext(OtelJavaContextAdapter(context, contextKeyRepository))
        }
        if (severityNumber != null) {
            builder.setSeverity(severityNumber.toOtelJavaSeverityNumber())
        }
        if (severityText != null) {
            builder.setSeverityText(severityText)
        }

        if (attributes != null) {
            val container = CompatMutableAttributeContainer()
            attributes(container)
            builder.setAllAttributes(container.otelJavaAttributes())
        }
        builder.emit()
    }
}
