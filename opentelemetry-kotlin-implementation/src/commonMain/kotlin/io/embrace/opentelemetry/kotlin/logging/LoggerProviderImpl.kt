package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.init.config.LoggingConfig
import io.embrace.opentelemetry.kotlin.logging.export.createCompositeLogRecordProcessor
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl

@OptIn(ExperimentalApi::class)
internal class LoggerProviderImpl(
    private val clock: Clock,
    loggingConfig: LoggingConfig,
    sdkFactory: SdkFactory,
) : LoggerProvider {

    private val apiProvider by lazy {
        ApiProviderImpl<Logger> { key ->
            val processor = when {
                loggingConfig.processors.isEmpty() -> null
                else -> createCompositeLogRecordProcessor(loggingConfig.processors)
            }
            LoggerImpl(
                clock,
                processor,
                sdkFactory,
                key,
                loggingConfig.resource,
                loggingConfig.logLimits
            )
        }
    }

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ): Logger {
        val key = apiProvider.createInstrumentationScopeInfo(name, version, schemaUrl, attributes)
        return apiProvider.getOrCreate(key)
    }
}
