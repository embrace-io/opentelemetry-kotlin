package io.embrace.opentelemetry.kotlin.logging.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.FakeInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.context.FakeContextKey
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext

@OptIn(ExperimentalApi::class)
class FakeReadableLogRecord(
    override val timestamp: Long? = 1000,
    override val observedTimestamp: Long? = 2000,
    override val context: Context? = FakeContext(mapOf(FakeContextKey<String>("key") to "value")),
    override val severityNumber: SeverityNumber? = SeverityNumber.WARN,
    override val severityText: String? = "warning",
    override val body: String? = "Hello, World!",
    override val attributes: Map<String, Any> = mapOf("key" to "value"),
    override val spanContext: FakeSpanContext = FakeSpanContext(),
    override val resource: Resource = FakeResource(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo = FakeInstrumentationScopeInfo()
) : ReadableLogRecord
