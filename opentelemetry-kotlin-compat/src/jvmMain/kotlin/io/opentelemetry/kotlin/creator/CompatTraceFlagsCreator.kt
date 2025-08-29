package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceFlagsAdapter

@OptIn(ExperimentalApi::class)
internal class CompatTraceFlagsCreator : TraceFlagsCreator {
    override val default: TraceFlags by lazy { TraceFlagsAdapter(OtelJavaTraceFlags.getDefault()) }

    override fun create(sampled: Boolean, random: Boolean): TraceFlags {
        return when {
            sampled && random -> TraceFlagsAdapter(OtelJavaTraceFlags.fromByte(0b00000011.toByte()))
            sampled && !random -> TraceFlagsAdapter(OtelJavaTraceFlags.getSampled())
            !sampled && random -> TraceFlagsAdapter(OtelJavaTraceFlags.fromByte(0b00000010.toByte()))
            else -> TraceFlagsAdapter(OtelJavaTraceFlags.getDefault())
        }
    }

    override fun fromHex(hex: String): TraceFlags {
        if (!hex.isValid()) {
            return TraceFlagsAdapter(OtelJavaTraceFlags.getDefault())
        }
        return TraceFlagsAdapter(OtelJavaTraceFlags.fromHex(hex, 0))
    }

    private fun String.isValid(): Boolean {
        return this.length == 2 && this.isValidHex()
    }
}
