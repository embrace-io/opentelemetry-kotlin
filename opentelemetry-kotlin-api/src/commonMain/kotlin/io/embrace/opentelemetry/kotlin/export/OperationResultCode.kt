package io.embrace.opentelemetry.kotlin.export

/**
 * Whether an operation was successful or not.
 */
public sealed class OperationResultCode {

    /**
     * Indicates that the operation was successful.
     */
    public object Success : OperationResultCode()

    /**
     * Indicates that the operation failed.
     */
    public object Failure : OperationResultCode()
}
