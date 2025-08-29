package io.opentelemetry.kotlin.logging

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import io.opentelemetry.kotlin.logging.model.SeverityNumber

@OptIn(ExperimentalApi::class)
class FakeLogger(
    val name: String
) : Logger {

    val logs: MutableList<FakeReadableLogRecord> = mutableListOf()

    override fun log(
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        logs.add(
            FakeReadableLogRecord(
                timestamp,
                observedTimestamp,
                severityNumber,
                severityText,
                body
            )
        )
    }
}
