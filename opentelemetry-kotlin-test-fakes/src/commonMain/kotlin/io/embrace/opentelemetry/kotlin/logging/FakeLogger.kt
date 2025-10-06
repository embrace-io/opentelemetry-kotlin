package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber

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
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        processTelemetry(null, timestamp, observedTimestamp, severityNumber, severityText, body)
    }

    override fun logEvent(
        eventName: String,
        body: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        context: Context?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        processTelemetry(eventName, timestamp, observedTimestamp, severityNumber, severityText, body)
    }

    private fun processTelemetry(
        eventName: String?,
        timestamp: Long?,
        observedTimestamp: Long?,
        severityNumber: SeverityNumber?,
        severityText: String?,
        body: String?
    ) {
        eventName.toString()
        logs.add(
            FakeReadableLogRecord(
                timestamp,
                observedTimestamp,
                severityNumber,
                severityText,
                body,
                eventName,
            )
        )
    }
}
