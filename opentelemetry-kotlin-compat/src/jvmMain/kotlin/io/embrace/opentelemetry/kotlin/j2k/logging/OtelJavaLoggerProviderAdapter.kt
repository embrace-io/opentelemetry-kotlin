package io.embrace.opentelemetry.kotlin.j2k.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLoggerBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLoggerProvider
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider

@OptIn(ExperimentalApi::class)
internal class OtelJavaLoggerProviderAdapter(
    private val loggerProvider: LoggerProvider
) : OtelJavaLoggerProvider {

    override fun loggerBuilder(instrumentationScopeName: String): OtelJavaLoggerBuilder = OtelJavaLoggerBuilderAdapter(
        loggerProvider,
        instrumentationScopeName
    )
}
