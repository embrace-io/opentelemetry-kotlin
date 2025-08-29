package io.opentelemetry.kotlin.logging

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.init.config.LogLimitConfig
import io.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.opentelemetry.kotlin.logging.model.LogRecordModel
import io.opentelemetry.kotlin.logging.model.ReadWriteLogRecordImpl
import io.opentelemetry.kotlin.logging.model.SeverityNumber
import io.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
internal class LoggerImpl(
    private val clock: Clock,
    private val processor: LogRecordProcessor,
    private val objectCreator: ObjectCreator,
    private val key: InstrumentationScopeInfo,
    private val resource: Resource,
    private val logLimitConfig: LogLimitConfig,
) : Logger {

    override fun log(
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        val attrs = MutableAttributeContainerImpl(logLimitConfig.attributeCountLimit).apply(attributes)
        val ctx = context ?: objectCreator.context.root()
        val log = LogRecordModel(
            attributeContainer = attrs,
            resource = resource,
            instrumentationScopeInfo = key,
            timestamp = timestamp ?: clock.now(),
            observedTimestamp = observedTimestamp ?: clock.now(),
            body = body,
            severityText = severityText,
            severityNumber = severityNumber ?: SeverityNumber.UNKNOWN,
            spanContext = objectCreator.span.fromContext(ctx).spanContext,
            logLimitConfig = logLimitConfig
        )
        processor.onEmit(ReadWriteLogRecordImpl(log), ctx)
    }
}
