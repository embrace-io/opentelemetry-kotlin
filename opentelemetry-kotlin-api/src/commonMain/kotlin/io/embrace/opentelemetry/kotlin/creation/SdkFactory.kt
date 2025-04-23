package io.embrace.opentelemetry.kotlin.creation

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

@ExperimentalApi
@SdkCreationDsl
public interface SdkFactory {
    public fun createOpenTelemetry(action: SdkParams.() -> Unit): OpenTelemetry
}
