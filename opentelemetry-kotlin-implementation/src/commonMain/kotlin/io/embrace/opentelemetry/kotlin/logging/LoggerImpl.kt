package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.init.config.LogLimitConfig
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.LogRecordModel
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecordImpl
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
internal class LoggerImpl(
    private val clock: Clock,
    private val processor: LogRecordProcessor?,
    sdkFactory: SdkFactory,
    private val key: InstrumentationScopeInfo,
    private val resource: Resource,
    private val logLimitConfig: LogLimitConfig,
) : Logger {

    private val root = sdkFactory.contextFactory.root()
    private val invalidSpanContext = sdkFactory.spanContextFactory.invalid
    private val spanFactory = sdkFactory.spanFactory

    override fun log(
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        processTelemetry(
            context = context,
            timestamp = timestamp,
            observedTimestamp = observedTimestamp,
            body = body,
            eventName = null,
            severityText = severityText,
            severityNumber = severityNumber,
            attributes = attributes
        )
    }

    override fun logEvent(
        eventName: String,
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        processTelemetry(
            context = context,
            timestamp = timestamp,
            observedTimestamp = observedTimestamp,
            body = body,
            eventName = eventName,
            severityText = severityText,
            severityNumber = severityNumber,
            attributes = attributes
        )
    }

    private fun processTelemetry(
        context: Context?,
        timestamp: Long?,
        observedTimestamp: Long?,
        body: String?,
        eventName: String?,
        severityText: String?,
        severityNumber: SeverityNumber?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        val ctx = context ?: root
        val spanContext = when (ctx) {
            root -> invalidSpanContext
            else -> spanFactory.fromContext(ctx).spanContext
        }

        val now = clock.now()
        val log = LogRecordModel(
            resource = resource,
            instrumentationScopeInfo = key,
            timestamp = timestamp ?: now,
            observedTimestamp = observedTimestamp ?: now,
            body = body,
            severityText = severityText,
            severityNumber = severityNumber ?: SeverityNumber.UNKNOWN,
            spanContext = spanContext,
            logLimitConfig = logLimitConfig,
            eventName = eventName,
        )
        if (attributes != null) {
            attributes(log)
        }
        processor?.onEmit(ReadWriteLogRecordImpl(log), ctx)
    }
}
