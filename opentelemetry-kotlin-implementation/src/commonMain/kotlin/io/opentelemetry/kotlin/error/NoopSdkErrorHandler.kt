package io.opentelemetry.kotlin.error

internal object NoopSdkErrorHandler : SdkErrorHandler {

    override fun onApiMisuse(
        api: String,
        details: String,
        severity: SdkErrorSeverity
    ) {
    }

    override fun onUserCodeError(
        exc: Throwable,
        details: String,
        severity: SdkErrorSeverity
    ) {
    }

    override fun onSdkCodeError(
        exc: Throwable,
        details: String,
        severity: SdkErrorSeverity
    ) {
    }
}
