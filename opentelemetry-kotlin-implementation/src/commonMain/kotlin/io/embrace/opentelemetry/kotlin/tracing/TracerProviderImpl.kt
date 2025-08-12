package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.init.config.TracingConfig
import io.embrace.opentelemetry.kotlin.provider.ApiProviderImpl
import io.embrace.opentelemetry.kotlin.tracing.export.CompositeSpanProcessor

@OptIn(ExperimentalApi::class)
internal class TracerProviderImpl(
    private val clock: Clock,
    tracingConfig: TracingConfig,
    objectCreator: ObjectCreator,
    sdkErrorHandler: SdkErrorHandler = NoopSdkErrorHandler,
) : TracerProvider {

    private val apiProvider = ApiProviderImpl<Tracer> { key ->
        val processor = CompositeSpanProcessor(tracingConfig.processors, sdkErrorHandler)
        TracerImpl(
            clock = clock,
            processor = processor,
            objectCreator = objectCreator,
            scope = key,
            resource = tracingConfig.resource
        )
    }

    override fun getTracer(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): Tracer {
        val key = apiProvider.createInstrumentationScopeInfo(
            name = name,
            version = version,
            schemaUrl = schemaUrl,
            attributes = attributes
        )
        return apiProvider.getOrCreate(key)
    }
}
