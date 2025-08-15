package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.random.Random

@OptIn(ExperimentalApi::class)
internal class TracingIdCreatorImpl(
    private val random: Random = Random.Default
) : TracingIdCreator {

    override fun generateTraceId(): String = randomHex(32)
    override fun generateSpanId(): String = randomHex(16)

    override val invalidTraceId: String = "00000000000000000000000000000000"
    override val invalidSpanId: String = "0000000000000000"

    private fun randomHex(count: Int): String {
        val bytes = random.nextBytes(count / 2)
        return buildString(count / 2) {
            for (byte in bytes) {
                val unsigned = byte.toInt() and 0xFF
                append(unsigned.toString(16).padStart(2, '0'))
            }
        }
    }
}
