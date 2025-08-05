package io.embrace.opentelemetry.kotlin.init.config

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Holds all information for how the SDK should behave. The implementation is immutable and should
 * therefore be considered threadsafe.
 */
@ThreadSafe
internal class SdkConfig(

    /**
     * Configuration for the Logging API.
     */
    val logger: LoggingConfig,

    /**
     * Configuration for the Tracing API.
     */
    val tracing: TracingConfig,
)
