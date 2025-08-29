package io.opentelemetry.kotlin.logging

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.opentelemetry.kotlin.error.SdkErrorHandler
import io.opentelemetry.kotlin.init.config.LoggingConfig
import io.opentelemetry.kotlin.logging.export.CompositeLogRecordProcessor
import io.opentelemetry.kotlin.provider.ApiProviderImpl

@OptIn(ExperimentalApi::class)
internal class LoggerProviderImpl(
    private val clock: Clock,
    loggingConfig: LoggingConfig,
    objectCreator: ObjectCreator,
    sdkErrorHandler: SdkErrorHandler = NoopSdkErrorHandler,
) : LoggerProvider {

    private val apiProvider = ApiProviderImpl<Logger> { key ->
        val processor = CompositeLogRecordProcessor(loggingConfig.processors, sdkErrorHandler)
        LoggerImpl(clock, processor, objectCreator, key, loggingConfig.resource, loggingConfig.logLimits)
    }

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): Logger {
        val key = apiProvider.createInstrumentationScopeInfo(name, version, schemaUrl, attributes)
        return apiProvider.getOrCreate(key)
    }
}
