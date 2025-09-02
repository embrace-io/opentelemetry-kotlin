package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaIdGenerator
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetryInstanceImpl
import io.embrace.opentelemetry.kotlin.factory.CompatSdkFactory
import io.embrace.opentelemetry.kotlin.factory.CompatTracingIdFactory
import io.embrace.opentelemetry.kotlin.factory.TracingIdFactory
import io.embrace.opentelemetry.kotlin.toOtelJavaApi
import kotlin.random.Random

@OptIn(ExperimentalApi::class)
internal class OtelKotlinHarness : OtelKotlinTestRule() {

    override val kotlinApi: OpenTelemetry by lazy {
        createCompatOpenTelemetryInstanceImpl(
            tracerProvider = tracerProviderConfig,
            loggerProvider = loggerProviderConfig,
            clock = clock,
            sdkFactory = CompatSdkFactory(tracingIdFactory = FakeTracingIdFactory())
        )
    }

    val javaApi by lazy {
        kotlinApi.toOtelJavaApi()
    }
}

@OptIn(ExperimentalApi::class)
private class FakeTracingIdFactory(
    private val impl: TracingIdFactory = CompatTracingIdFactory(),
) : TracingIdFactory by impl, OtelJavaIdGenerator {

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
