package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer

@OptIn(ExperimentalApi::class)
class FakeTracerProvider : TracerProvider {

    val map = mutableMapOf<String, FakeTracer>()

    override fun getTracer(
        name: String,
        version: String?,
        schemaUrl: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ): Tracer = map.getOrPut(name) {
        FakeTracer(name)
    }
}
