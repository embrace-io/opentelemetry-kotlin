package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
public class TraceFlagsAdapter(
    traceFlags: OtelJavaTraceFlags
) : TraceFlags {

    override val isSampled: Boolean = traceFlags.isSampled
    override val isRandom: Boolean = traceFlags.asHex()[0] == '1'
    override val hex: String = traceFlags.asHex()
}
