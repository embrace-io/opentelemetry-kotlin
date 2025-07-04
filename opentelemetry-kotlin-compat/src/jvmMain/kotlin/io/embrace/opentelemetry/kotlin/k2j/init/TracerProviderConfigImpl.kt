package io.embrace.opentelemetry.kotlin.k2j.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSdkTracerProvider
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.init.SpanLimitsConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.j2k.tracing.export.OtelJavaSpanProcessorAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.AttributeContainerImpl
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
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

    override fun resource(action: AttributeContainer.() -> Unit) {
        val attrs = AttributeContainerImpl().apply(action).otelJavaAttributes()
        builder.setResource(Resource.create(attrs))
    }

    override fun spanLimits(action: SpanLimitsConfigDsl.() -> Unit) {
        builder.setSpanLimits(SpanLimitsConfigImpl().apply(action).build())
    }

    override fun addSpanProcessor(processor: SpanProcessor) {
        builder.addSpanProcessor(OtelJavaSpanProcessorAdapter(processor))
    }

    fun build(): TracerProvider = TracerProviderAdapter(builder.build(), clock)
}
