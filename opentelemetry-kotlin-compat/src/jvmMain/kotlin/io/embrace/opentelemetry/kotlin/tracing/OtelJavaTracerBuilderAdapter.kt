package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerBuilder

@OptIn(ExperimentalApi::class)
internal class OtelJavaTracerBuilderAdapter(
    private val tracerProvider: TracerProvider,
    private val instrumentationScopeName: String
) : OtelJavaTracerBuilder {
    private var instrumentationScopeVersion: String? = null
    private var schemaUrl: String? = null

    override fun setInstrumentationVersion(instrumentationScopeVersion: String): OtelJavaTracerBuilder {
        this.instrumentationScopeVersion = instrumentationScopeVersion
        return this
    }

    override fun setSchemaUrl(schemaUrl: String): OtelJavaTracerBuilder {
        this.schemaUrl = schemaUrl
        return this
    }

    override fun build(): OtelJavaTracer {
        val tracer = tracerProvider.getTracer(
            name = instrumentationScopeName,
            version = instrumentationScopeVersion,
            schemaUrl = schemaUrl
        )
        return OtelJavaTracerAdapter(tracer)
    }
}
