package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.random.Random

@OptIn(ExperimentalApi::class)
internal class TracingIdFactoryImpl(
    private val random: Random = Random.Default
) : TracingIdFactory {

    override fun generateTraceId(): String = randomHex(32)
    override fun generateSpanId(): String = randomHex(16)

    override val invalidTraceId: String = "00000000000000000000000000000000"
    override val invalidSpanId: String = "0000000000000000"

    // follows approach taken in opentelemetry-java (optimized for speed)

    private val hexChars = "0123456789abcdef".toCharArray()

    private fun randomHex(charCount: Int): String {
        var bytes: ByteArray
        do {
            bytes = ByteArray(charCount / 2).apply { // 2 chars per byte
                random.nextBytes(this)
            }
        } while (bytes.all { it == 0.toByte() }) // reject all-zero
        return toHex(bytes)
    }

    private fun toHex(bytes: ByteArray): String {
        val result = CharArray(bytes.size * 2)
        var k = 0
        for (byte in bytes) {
            val i = byte.toInt() and 0xFF
            result[k++] = hexChars[i ushr 4]
            result[k++] = hexChars[i and 0x0F]
        }
        return result.concatToString()
    }
}
