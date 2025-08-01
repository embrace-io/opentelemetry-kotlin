package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

@OptIn(ExperimentalApi::class)
internal data class TestHarnessConfig(
    val schemaUrl: String? = null,
    val attributes: (MutableAttributeContainer.() -> Unit)? = null,
    val spanProcessors: List<SpanProcessor> = emptyList(),
    val logRecordProcessors: List<LogRecordProcessor> = emptyList()
)
