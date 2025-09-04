package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
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
    private val processor: LogRecordProcessor,
    private val sdkFactory: SdkFactory,
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
        val ctx = context ?: sdkFactory.contextFactory.root()
        val log = LogRecordModel(
            attributeContainer = attrs,
            resource = resource,
            instrumentationScopeInfo = key,
            timestamp = timestamp ?: clock.now(),
            observedTimestamp = observedTimestamp ?: clock.now(),
            body = body,
            severityText = severityText,
            severityNumber = severityNumber ?: SeverityNumber.UNKNOWN,
            spanContext = sdkFactory.spanFactory.fromContext(ctx).spanContext,
            logLimitConfig = logLimitConfig
        )
        processor.onEmit(ReadWriteLogRecordImpl(log), ctx)
    }
}
