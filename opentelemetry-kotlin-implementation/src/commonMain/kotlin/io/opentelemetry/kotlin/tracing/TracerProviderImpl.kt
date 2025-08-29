package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.opentelemetry.kotlin.error.SdkErrorHandler
import io.opentelemetry.kotlin.init.config.TracingConfig
import io.opentelemetry.kotlin.provider.ApiProviderImpl
import io.opentelemetry.kotlin.tracing.export.CompositeSpanProcessor

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
            resource = tracingConfig.resource,
            spanLimitConfig = tracingConfig.spanLimits
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
