package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerProvider
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.init.CompatSpanLimitsConfig
import java.util.concurrent.ConcurrentHashMap

@ExperimentalApi
internal class TracerProviderAdapter(
    private val tracerProvider: OtelJavaTracerProvider,
    private val clock: Clock,
    private val spanLimitsConfig: CompatSpanLimitsConfig,
) : TracerProvider {

    private val map = ConcurrentHashMap<String, TracerAdapter>()

    override fun getTracer(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ): Tracer {
        val key = name.plus(version).plus(schemaUrl)

        return map.getOrPut(key) {
            val tracerBuilder = tracerProvider.tracerBuilder(name)

            schemaUrl?.let(tracerBuilder::setSchemaUrl)
            version?.let(tracerBuilder::setInstrumentationVersion)
            val tracer = tracerBuilder.build()
            TracerAdapter(tracer, clock, spanLimitsConfig)
        }
    }
}
