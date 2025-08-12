package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.init.config.TracingConfig
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl
import io.embrace.opentelemetry.kotlin.tracing.export.CompositeSpanProcessor

@OptIn(ExperimentalApi::class)
internal class TracerProviderImpl(
    private val clock: Clock,
    tracingConfig: TracingConfig,
    sdkErrorHandler: SdkErrorHandler = NoopSdkErrorHandler,
) : TracerProvider {

    private val apiProvider = ApiProviderImpl<Tracer> { key ->
        val processor = CompositeSpanProcessor(tracingConfig.processors, sdkErrorHandler)
        TracerImpl(clock, processor, key)
    }

    override fun getTracer(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): Tracer {
        val key = apiProvider.createKey(attributes, name, version, schemaUrl)
        return apiProvider.getOrCreate(key)
    }
}
