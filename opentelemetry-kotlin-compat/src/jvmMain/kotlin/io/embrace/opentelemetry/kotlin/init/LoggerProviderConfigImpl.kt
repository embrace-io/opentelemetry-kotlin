package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkLoggerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkLoggerProviderBuilder
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.export.OtelJavaLogRecordProcessorAdapter

@ExperimentalApi
internal class LoggerProviderConfigImpl(
    clock: Clock
) : LoggerProviderConfigDsl {

    private val builder: OtelJavaSdkLoggerProviderBuilder = OtelJavaSdkLoggerProvider.builder()

    init {
        builder.setClock(OtelJavaClockWrapper(clock))
    }

    override fun resource(schemaUrl: String?, attributes: MutableAttributeContainer.() -> Unit) {
        val attrs = MutableAttributeContainerImpl().apply(attributes).otelJavaAttributes()
        builder.setResource(OtelJavaResource.create(attrs, schemaUrl))
    }

    override fun addLogRecordProcessor(processor: LogRecordProcessor) {
        builder.addLogRecordProcessor(OtelJavaLogRecordProcessorAdapter(processor))
    }

    override fun logLimits(action: LogLimitsConfigDsl.() -> Unit) {
        builder.setLogLimits { LogLimitsConfigImpl().apply(action).build() }
    }

    fun build(): LoggerProvider = LoggerProviderAdapter(builder.build())
}
