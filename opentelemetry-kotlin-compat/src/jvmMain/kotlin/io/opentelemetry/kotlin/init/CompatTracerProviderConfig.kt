package io.opentelemetry.kotlin.init

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.opentelemetry.kotlin.aliases.OtelJavaResource
import io.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProviderBuilder
import io.opentelemetry.kotlin.attributes.CompatMutableAttributeContainer
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.tracing.TracerProvider
import io.opentelemetry.kotlin.tracing.TracerProviderAdapter
import io.opentelemetry.kotlin.tracing.export.OtelJavaSpanProcessorAdapter
import io.opentelemetry.kotlin.tracing.export.SpanProcessor

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
        val attrs = CompatMutableAttributeContainer().apply(attributes).otelJavaAttributes()
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
