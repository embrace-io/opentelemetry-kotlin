package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.LogRecordModel
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecordImpl
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource

@Suppress("unused")
@OptIn(ExperimentalApi::class)
internal class LoggerImpl(
    private val clock: Clock,
    private val processor: LogRecordProcessor,
    private val objectCreator: ObjectCreator,
    private val key: InstrumentationScopeInfo,
    private val resource: Resource,
) : Logger {

    override fun log(
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        val attrs = MutableAttributeContainerImpl().apply(attributes)
        val log = LogRecordModel(attrs)
        val ctx = context ?: objectCreator.context.root()
        processor.onEmit(ReadWriteLogRecordImpl(log), ctx)
    }
}
