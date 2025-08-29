package io.opentelemetry.kotlin.integration.test

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.OpenTelemetry
import io.opentelemetry.kotlin.OpenTelemetryInstance
import io.opentelemetry.kotlin.creator.ObjectCreatorImpl
import io.opentelemetry.kotlin.creator.TracingIdCreatorImpl
import io.opentelemetry.kotlin.default
import io.opentelemetry.kotlin.framework.OtelKotlinTestRule
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
            objectCreator = ObjectCreatorImpl(idCreator = TracingIdCreatorImpl(Random(0)))
        )
    }
}
