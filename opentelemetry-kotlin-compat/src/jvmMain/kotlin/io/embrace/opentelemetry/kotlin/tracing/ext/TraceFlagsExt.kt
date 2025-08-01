package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@OptIn(ExperimentalApi::class)
internal fun TraceFlags.toOtelJavaTraceFlags(): OtelJavaTraceFlags {
    val sb = StringBuilder()
    sb.append(if (isRandom) "1" else "0")
    sb.append(if (isSampled) "1" else "0")
    return OtelJavaTraceFlags.fromHex(sb.toString(), 0)
}
