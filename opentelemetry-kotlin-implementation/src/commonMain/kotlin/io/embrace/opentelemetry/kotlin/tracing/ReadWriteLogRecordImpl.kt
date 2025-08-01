package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
public class ReadWriteLogRecordImpl(
    override var timestamp: Long?,
    override var observedTimestamp: Long?,
    override var severityNumber: SeverityNumber?,
    override var severityText: String?,
    override var body: String?,
    override val attributes: MutableMap<String, Any>,
    override var spanContext: SpanContext,
    override val context: Context?,
    override val resource: Resource?,
    override val instrumentationScopeInfo: InstrumentationScopeInfo?,
    private val mutableAttributeContainer: MutableAttributeContainer,
) : ReadWriteLogRecord, MutableAttributeContainer by mutableAttributeContainer
