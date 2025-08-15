package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.init.config.LoggingConfig
import io.embrace.opentelemetry.kotlin.logging.export.CompositeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl

@OptIn(ExperimentalApi::class)
internal class LoggerProviderImpl(
    private val clock: Clock,
    loggingConfig: LoggingConfig,
    objectCreator: ObjectCreator,
    sdkErrorHandler: SdkErrorHandler = NoopSdkErrorHandler,
) : LoggerProvider {

    private val apiProvider = ApiProviderImpl<Logger> { key ->
        val processor = CompositeLogRecordProcessor(loggingConfig.processors, sdkErrorHandler)
        LoggerImpl(clock, processor, objectCreator, key, loggingConfig.resource)
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
