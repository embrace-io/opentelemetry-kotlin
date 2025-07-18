package io.embrace.opentelemetry.kotlin.k2j.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkLoggerProvider
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.init.LogLimitsConfigDsl
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.j2k.logging.export.OtelJavaLogRecordProcessorAdapter
import io.embrace.opentelemetry.kotlin.k2j.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.AttributeContainerImpl
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.opentelemetry.sdk.logs.SdkLoggerProviderBuilder
import io.opentelemetry.sdk.resources.Resource

@ExperimentalApi
internal class LoggerProviderConfigImpl(
    clock: Clock
) : LoggerProviderConfigDsl {

    private val builder: SdkLoggerProviderBuilder = OtelJavaSdkLoggerProvider.builder()

    init {
        builder.setClock(OtelJavaClockWrapper(clock))
    }

    override fun resource(action: AttributeContainer.() -> Unit) {
        val attrs = AttributeContainerImpl().apply(action).otelJavaAttributes()
        builder.setResource(Resource.create(attrs))
    }

    override fun addLogRecordProcessor(processor: LogRecordProcessor) {
        builder.addLogRecordProcessor(OtelJavaLogRecordProcessorAdapter(processor))
    }

    override fun logLimits(action: LogLimitsConfigDsl.() -> Unit) {
        builder.setLogLimits { LogLimitsConfigImpl().apply(action).build() }
    }

    fun build(): LoggerProvider = LoggerProviderAdapter(builder.build())
}
