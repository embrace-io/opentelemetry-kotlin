@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource

internal class FakeReadableLogRecord(
    override val timestamp: Long? = 1000,
    override val observedTimestamp: Long? = 2000,
    override val context: Context? = FakeContext(mapOf(FakeContextKey<String>("key") to "value")),
    override val severityNumber: SeverityNumber? = SeverityNumber.WARN,
    override val severityText: String? = "warning",
    override val body: String? = "Hello, World!",
    override val attributes: Map<String, Any> = mapOf("key" to "value"),
    override val spanContext: FakeSpanContext = FakeSpanContext(),
    override val resource: Resource? = FakeResource(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo? = FakeInstrumentationScopeInfo()
) : ReadableLogRecord
