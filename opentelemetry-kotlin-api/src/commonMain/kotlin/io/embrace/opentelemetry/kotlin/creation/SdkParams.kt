package io.embrace.opentelemetry.kotlin.creation

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi

@SdkCreationDsl
@ExperimentalApi
public interface SdkParams {

    @SdkCreationDsl
    public var clock: Clock?

    @SdkCreationDsl
    public val loggerProviderParams: LoggerProviderParams

    @SdkCreationDsl
    public val tracerProviderParams: TracerProviderParams

    @SdkCreationDsl
    public fun loggerProviderParams(action: LoggerProviderParams.() -> Unit)

    @SdkCreationDsl
    public fun tracerProviderParams(action: TracerProviderParams.() -> Unit)
}
