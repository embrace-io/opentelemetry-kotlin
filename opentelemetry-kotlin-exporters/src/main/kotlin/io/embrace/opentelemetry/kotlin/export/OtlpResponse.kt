package io.embrace.opentelemetry.kotlin.export

internal sealed class OtlpResponse(val statusCode: Int) {
    object Success : OtlpResponse(200)
    class ClientError(statusCode: Int, val errorMessage: String?) : OtlpResponse(statusCode)
    class ServerError(statusCode: Int, val errorMessage: String?) : OtlpResponse(statusCode)
    object Unknown : OtlpResponse(-1)
}
