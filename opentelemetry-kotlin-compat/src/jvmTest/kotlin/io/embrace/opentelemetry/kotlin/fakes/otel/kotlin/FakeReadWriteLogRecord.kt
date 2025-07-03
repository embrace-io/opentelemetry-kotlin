@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

internal class FakeReadWriteLogRecord(
    override var timestamp: Long? = 1000,
    override var observedTimestamp: Long? = 2000,
    override var severityNumber: SeverityNumber? = SeverityNumber.WARN,
    override var severityText: String? = "warning",
    override var body: String? = "Hello, World!",
    override var attributes: MutableMap<String, Any> = mutableMapOf("key" to "value"),
    override var spanContext: SpanContext = FakeSpanContext(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo = FakeInstrumentationScopeInfo(),
    override val resource: Resource = FakeResource(),
    override val context: Context = FakeContext()
) : ReadWriteLogRecord
