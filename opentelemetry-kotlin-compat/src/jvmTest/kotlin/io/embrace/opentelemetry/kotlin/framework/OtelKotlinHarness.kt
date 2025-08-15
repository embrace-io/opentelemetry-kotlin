package io.embrace.opentelemetry.kotlin.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.createOpenTelemetryKotlin
import io.embrace.opentelemetry.kotlin.decorateKotlinApi

@OptIn(ExperimentalApi::class)
internal class OtelKotlinHarness : OtelKotlinTestRule() {

    override val kotlinApi: OpenTelemetry by lazy {
        OpenTelemetryInstance.createOpenTelemetryKotlin(
            clock = clock,
            tracerProvider = tracerProviderConfig,
            loggerProvider = loggerProviderConfig,
        )
    }

    val javaApi by lazy {
        OpenTelemetryInstance.decorateKotlinApi(kotlinApi)
    }
}
