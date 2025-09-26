package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OtlpResponse.ClientError
import io.embrace.opentelemetry.kotlin.export.OtlpResponse.ServerError
import io.embrace.opentelemetry.kotlin.export.OtlpResponse.Success
import io.embrace.opentelemetry.kotlin.export.OtlpResponse.Unknown
import io.embrace.opentelemetry.kotlin.logging.export.toExportLogsServiceRequest
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.export.toExportTraceServiceRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.compression.compress
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceResponse
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceResponse

@OptIn(ExperimentalApi::class)
internal class OtlpClient(
    private val baseUrl: String,
    private val httpClient: HttpClient = defaultHttpClient
) {

    private val contentType = ContentType.Companion.parse("application/x-protobuf")

    suspend fun exportLogs(telemetry: List<ReadableLogRecord>): OtlpResponse = exportTelemetry(
        OtlpEndpoint.Logs,
        telemetry.toExportLogsServiceRequest()::toByteArray,
        ::readLogRecordErrorMessage
    )

    suspend fun exportTraces(telemetry: List<SpanData>): OtlpResponse = exportTelemetry(
        OtlpEndpoint.Traces,
        telemetry.toExportTraceServiceRequest()::toByteArray,
        ::readTracesErrorMessage
    )

    private suspend fun exportTelemetry(
        endpoint: OtlpEndpoint,
        requestSerializer: () -> ByteArray,
        onError: (body: ByteArray) -> String?,
    ): OtlpResponse {
        return try {
            val url = "$baseUrl/${endpoint.path}"
            val response = httpClient.post(url) {
                compress("gzip")
                contentType(contentType)
                setBody(requestSerializer())
            }
            val code = response.status.value
            when {
                code == 200 -> Success
                code >= 400 && code <= 499 -> ClientError(code, onError(response.bodyAsBytes()))
                code >= 500 && code <= 599 -> ServerError(code, onError(response.bodyAsBytes()))
                else -> Unknown
            }
        } catch (ignored: HttpRequestTimeoutException) {
            Unknown
        }
    }

    private fun readTracesErrorMessage(bytes: ByteArray): String? {
        val response = ExportTraceServiceResponse.parseFrom(bytes)
        return response.partialSuccess.errorMessage
    }

    private fun readLogRecordErrorMessage(bytes: ByteArray): String? {
        val response = ExportLogsServiceResponse.parseFrom(bytes)
        return response.partialSuccess.errorMessage
    }
}
