package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLoggerProvider
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import java.util.concurrent.ConcurrentHashMap

@ExperimentalApi
internal class LoggerProviderAdapter(private val impl: OtelJavaLoggerProvider) : LoggerProvider {

    private val map = ConcurrentHashMap<String, LoggerAdapter>()

    override fun getLogger(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: AttributeContainer.() -> Unit
    ): Logger {
        val key = name.plus(version).plus(schemaUrl)
        return map.getOrPut(key) {
            val builder = impl.loggerBuilder(name)

            if (schemaUrl != null) {
                builder.setSchemaUrl(schemaUrl)
            }
            if (version != null) {
                builder.setInstrumentationVersion(version)
            }
            LoggerAdapter(builder.build())
        }
    }
}
