package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.TraceFlags

internal class TraceFlagsAdapter(
    traceFlags: io.opentelemetry.api.trace.TraceFlags
) : TraceFlags {

    override val isSampled: Boolean = traceFlags.isSampled
    override val isRandom: Boolean = traceFlags.asHex()[0] == '1'
}
