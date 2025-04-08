package io.embrace.opentelemetry.kotlin.k2j.creation

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.creation.SdkFactory
import io.embrace.opentelemetry.kotlin.creation.SdkParams
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaOpenTelemetry

@ExperimentalApi
public class CompatSdkFactory : SdkFactory {

    /**
     * Creates an instance of the Kotlin OTel API that is backed by the Java SDK implementation.
     */
    public fun createOpenTelemetry(otelJava: OtelJavaOpenTelemetry): OpenTelemetry = OpenTelemetrySdk(otelJava)

    override fun createOpenTelemetry(action: SdkParams.() -> Unit): OpenTelemetry {
        val params = SdkParamsImpl()
        action(params)

        // TODO: read from params to create OTel instance.

        TODO("Not yet implemented")
    }
}
