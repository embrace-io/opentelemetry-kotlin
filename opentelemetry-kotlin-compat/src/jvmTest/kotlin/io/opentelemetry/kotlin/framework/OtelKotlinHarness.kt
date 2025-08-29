package io.opentelemetry.kotlin.framework

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.OpenTelemetry
import io.opentelemetry.kotlin.OpenTelemetryInstance
import io.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.opentelemetry.kotlin.createOpenTelemetryKotlin
import io.opentelemetry.kotlin.creator.CompatObjectCreator
import io.opentelemetry.kotlin.creator.CompatTracingIdCreator
import io.opentelemetry.kotlin.creator.TracingIdCreator
import io.opentelemetry.kotlin.decorateKotlinApi
import kotlin.random.Random

@OptIn(ExperimentalApi::class)
internal class OtelKotlinHarness : OtelKotlinTestRule() {

    override val kotlinApi: OpenTelemetry by lazy {
        OpenTelemetryInstance.createOpenTelemetryKotlin(
            clock = clock,
            tracerProvider = tracerProviderConfig,
            loggerProvider = loggerProviderConfig,
            objectCreator = CompatObjectCreator(idCreator = FakeTracingIdCreator())
        )
    }

    val javaApi by lazy {
        OpenTelemetryInstance.decorateKotlinApi(kotlinApi)
    }
}

@OptIn(ExperimentalApi::class)
private class FakeTracingIdCreator(
    private val impl: TracingIdCreator = CompatTracingIdCreator(),
) : TracingIdCreator by impl, OtelJavaIdGenerator {

    private val random: Random = Random(0)

    override fun generateSpanId(): String = randomHex(16)
    override fun generateTraceId(): String = randomHex(32)

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
