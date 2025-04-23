package io.embrace.opentelemetry.kotlin.k2j.creation

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.creation.LoggerProviderParams
import io.embrace.opentelemetry.kotlin.creation.SdkParams
import io.embrace.opentelemetry.kotlin.creation.TracerProviderParams

@ExperimentalApi
internal class SdkParamsImpl : SdkParams {

    override var clock: Clock? = null
    override val loggerProviderParams = LoggerProviderParamsImpl()
    override val tracerProviderParams = TracerProviderParamsImpl()

    override fun loggerProviderParams(action: LoggerProviderParams.() -> Unit) {
        action(loggerProviderParams)
    }

    override fun tracerProviderParams(action: TracerProviderParams.() -> Unit) {
        action(tracerProviderParams)
    }
}
