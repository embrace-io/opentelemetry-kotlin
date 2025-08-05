package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.export.OtelJavaSpanProcessorAdapter
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder

@ExperimentalApi
internal class TracerProviderConfigImpl(
    private val clock: Clock
) : TracerProviderConfigDsl {

    private val builder: SdkTracerProviderBuilder = OtelJavaSdkTracerProvider.builder()

    init {
        builder.setClock(OtelJavaClockWrapper(clock))
    }

    override fun resource(schemaUrl: String?, attributes: MutableAttributeContainer.() -> Unit) {
        val attrs = MutableAttributeContainerImpl().apply(attributes).otelJavaAttributes()
        builder.setResource(Resource.create(attrs, schemaUrl))
    }

    override fun spanLimits(action: SpanLimitsConfigDsl.() -> Unit) {
        builder.setSpanLimits(SpanLimitsConfigImpl().apply(action).build())
    }

    override fun addSpanProcessor(processor: SpanProcessor) {
        builder.addSpanProcessor(OtelJavaSpanProcessorAdapter(processor))
    }

    fun build(): TracerProvider = TracerProviderAdapter(builder.build(), clock)
}
