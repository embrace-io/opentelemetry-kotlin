package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.init.LogLimitsConfigDsl
import io.embrace.opentelemetry.kotlin.init.SpanLimitsConfigDsl
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

@OptIn(ExperimentalApi::class)
internal data class TestHarnessConfig(
    var schemaUrl: String? = null,
    var attributes: (MutableAttributeContainer.() -> Unit)? = null,
    val spanProcessors: MutableList<SpanProcessor> = mutableListOf(),
    val logRecordProcessors: MutableList<LogRecordProcessor> = mutableListOf(),
    var spanLimits: SpanLimitsConfigDsl.() -> Unit = {},
    var logLimits: LogLimitsConfigDsl.() -> Unit = {},
)
