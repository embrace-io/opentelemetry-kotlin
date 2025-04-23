package io.embrace.opentelemetry.kotlin.creation

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi

@SdkCreationDsl
@ExperimentalApi
public interface TracerProviderParams {
    public var clock: Clock?
}
