package io.embrace.opentelemetry.kotlin.k2j.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaLoggerProvider
import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider

@ExperimentalApi
internal class LoggerProviderAdapter(private val impl: OtelJavaLoggerProvider) : LoggerProvider {

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: AttributeContainer.() -> Unit
    ): Logger {
        val builder = impl.loggerBuilder(name)

        if (schemaUrl != null) {
            builder.setSchemaUrl(schemaUrl)
        }
        if (version != null) {
            builder.setInstrumentationVersion(version)
        }
        return LoggerAdapter(builder.build())
    }
}
