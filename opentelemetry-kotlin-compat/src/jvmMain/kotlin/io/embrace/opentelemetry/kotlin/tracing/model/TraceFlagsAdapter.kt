package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags

@OptIn(ExperimentalApi::class)
internal class TraceFlagsAdapter(
    traceFlags: OtelJavaTraceFlags
) : TraceFlags {

    override val isSampled: Boolean = traceFlags.isSampled
    override val isRandom: Boolean = traceFlags.asHex()[0] == '1'
    override val hex: String = traceFlags.asHex()
}
