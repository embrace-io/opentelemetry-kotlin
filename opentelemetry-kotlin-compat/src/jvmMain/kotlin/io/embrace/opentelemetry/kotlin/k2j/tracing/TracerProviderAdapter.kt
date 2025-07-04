package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerProvider
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import java.util.concurrent.ConcurrentHashMap

@ExperimentalApi
public class TracerProviderAdapter(
    private val tracerProvider: OtelJavaTracerProvider,
    private val clock: Clock,
) : TracerProvider {

    private val map = ConcurrentHashMap<String, TracerAdapter>()

    override fun getTracer(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: AttributeContainer.() -> Unit
    ): Tracer {
        val key = name.plus(version).plus(schemaUrl)

        return map.getOrPut(key) {
            val tracerBuilder = tracerProvider.tracerBuilder(name)

            schemaUrl?.let(tracerBuilder::setSchemaUrl)
            version?.let(tracerBuilder::setInstrumentationVersion)
            val tracer = tracerBuilder.build()
            TracerAdapter(tracer, clock)
        }
    }
}
