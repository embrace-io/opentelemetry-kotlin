package io.embrace.opentelemetry.kotlin.k2j.creation

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.creation.TracerProviderParams

@ExperimentalApi
internal class TracerProviderParamsImpl(
    override var clock: Clock? = null
) : TracerProviderParams
