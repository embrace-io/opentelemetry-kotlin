package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.init.config.LoggingConfig
import io.embrace.opentelemetry.kotlin.logging.export.CompositeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl

@OptIn(ExperimentalApi::class)
public class LoggerProviderImpl(
    private val clock: Clock,
    loggingConfig: LoggingConfig,
    sdkFactory: SdkFactory,
    sdkErrorHandler: SdkErrorHandler = NoopSdkErrorHandler,
) : LoggerProvider {

    private val apiProvider = ApiProviderImpl<Logger> { key ->
        val processor = CompositeLogRecordProcessor(loggingConfig.processors, sdkErrorHandler)
        LoggerImpl(clock, processor, sdkFactory, key, loggingConfig.resource, loggingConfig.logLimits)
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
