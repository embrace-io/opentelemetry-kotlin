package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber

@ExperimentalApi
internal object NoopLogger : Logger {
    override fun log(
        body: String?,
        timestampNs: Long?,
        observedTimestampNs: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: AttributeContainer.() -> Unit
    ) {
    }
}
