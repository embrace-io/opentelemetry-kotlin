package io.embrace.opentelemetry.kotlin.integration.test

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.default
import io.embrace.opentelemetry.kotlin.factory.SdkFactoryImpl
import io.embrace.opentelemetry.kotlin.factory.TracingIdFactoryImpl
import io.embrace.opentelemetry.kotlin.framework.OtelKotlinTestRule
import kotlin.random.Random

/**
 * Configures opentelemetry-kotlin to run for integration tests so that exported logs/traces
 * can be verified against expected output.
 */
@OptIn(ExperimentalApi::class)
internal class IntegrationTestHarness : OtelKotlinTestRule() {
    override val kotlinApi: OpenTelemetry by lazy {
        OpenTelemetryInstance.default(
            tracerProvider = tracerProviderConfig,
            loggerProvider = loggerProviderConfig,
            clock = clock,
            sdkFactory = SdkFactoryImpl(tracingIdFactory = TracingIdFactoryImpl(Random(0)))
        )
    }
}
