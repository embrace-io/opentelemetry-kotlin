package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProviderBuilder
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.export.OtelJavaSpanProcessorAdapter
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

@ExperimentalApi
internal class CompatTracerProviderConfig(
    private val clock: Clock,
    objectCreator: ObjectCreator,
) : TracerProviderConfigDsl {

    private val builder: OtelJavaSdkTracerProviderBuilder = OtelJavaSdkTracerProvider.builder()

    init {
        builder.setClock(OtelJavaClockWrapper(clock))

        val idGenerator = objectCreator.idCreator
        if (idGenerator is OtelJavaIdGenerator) {
            builder.setIdGenerator(idGenerator)
        }
    }

    override fun resource(schemaUrl: String?, attributes: MutableAttributeContainer.() -> Unit) {
        val attrs = MutableAttributeContainerImpl().apply(attributes).otelJavaAttributes()
        builder.setResource(OtelJavaResource.create(attrs, schemaUrl))
    }

    override fun spanLimits(action: SpanLimitsConfigDsl.() -> Unit) {
        builder.setSpanLimits(CompatSpanLimitsConfig().apply(action).build())
    }

    override fun addSpanProcessor(processor: SpanProcessor) {
        builder.addSpanProcessor(OtelJavaSpanProcessorAdapter(processor))
    }

    fun build(): TracerProvider = TracerProviderAdapter(builder.build(), clock)
}
