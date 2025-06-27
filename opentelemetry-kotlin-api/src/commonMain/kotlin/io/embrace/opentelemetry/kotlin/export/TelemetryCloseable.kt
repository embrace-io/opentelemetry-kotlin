package io.embrace.opentelemetry.kotlin.export

/**
 * Implementations of this interface handle telemetry. The interface allows callers to request a flush of any
 * buffered telemetry, and to perform any necessary cleanup tasks when the SDK is shutting down.
 */
public interface TelemetryCloseable {

    /**
     * Requests the implementation to flush any buffered telemetry.
     */
    public fun forceFlush(): OperationResultCode

    /**
     * Shuts down the implementation and completes cleanup tasks necessary.
     */
    public fun shutdown(): OperationResultCode
}
