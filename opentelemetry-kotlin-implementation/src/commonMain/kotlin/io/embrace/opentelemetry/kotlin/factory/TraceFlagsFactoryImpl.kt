package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.TraceFlagsImpl
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

@ExperimentalApi
internal class TraceFlagsFactoryImpl : TraceFlagsFactory {
    override val default: TraceFlags by lazy { TraceFlagsImpl(isSampled = true, isRandom = false) }

    override fun create(sampled: Boolean, random: Boolean): TraceFlags {
        return TraceFlagsImpl(isSampled = sampled, isRandom = random)
    }

    override fun fromHex(hex: String): TraceFlags {
        if (!hex.isValid()) {
            return TraceFlagsImpl(isSampled = false, isRandom = false)
        }

        val byte = hex.toInt(16)
        return TraceFlagsImpl(
            isSampled = (byte and 0b00000001) != 0,
            isRandom = (byte and 0b00000010) != 0
        )
    }

    private fun String.isValid(): Boolean {
        return this.length == 2 && this.isValidHex()
    }
}
