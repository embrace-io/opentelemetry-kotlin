package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber

@Suppress("unused")
@OptIn(ExperimentalApi::class)
internal class LoggerImpl(
    private val clock: Clock,
    private val key: InstrumentationScopeInfo
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
        throw UnsupportedOperationException()
    }
}
