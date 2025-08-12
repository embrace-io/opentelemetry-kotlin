package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.init.config.LoggingConfig
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class LoggerProviderImpl(
    private val clock: Clock,
    loggingConfig: LoggingConfig,
    sdkErrorHandler: SdkErrorHandler = NoopSdkErrorHandler,
) : LoggerProvider {

    private val apiProvider = ApiProviderImpl<Logger> { key ->
        LoggerImpl(clock, key)
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
