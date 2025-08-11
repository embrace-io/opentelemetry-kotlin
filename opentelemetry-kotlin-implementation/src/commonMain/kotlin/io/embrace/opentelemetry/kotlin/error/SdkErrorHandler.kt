package io.embrace.opentelemetry.kotlin.error

/**
 * Handles errors and misuse of the SDK.
 */
internal interface SdkErrorHandler {

    /**
     * Called when the API was misused (e.g. passing an empty string to something that requires non-empty)
     */
    fun onApiMisuse(api: String, details: String, severity: SdkErrorSeverity)

    /**
     * Called when user-supplied code throws
     */
    fun onUserCodeError(exc: Throwable, details: String, severity: SdkErrorSeverity)

    /**
     * Called when SDK code throws
     */
    fun onSdkCodeError(exc: Throwable, details: String, severity: SdkErrorSeverity)
}
