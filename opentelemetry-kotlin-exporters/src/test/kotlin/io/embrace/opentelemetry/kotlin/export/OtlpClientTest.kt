package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.export.toExportLogsServiceRequest
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.tracing.data.FakeSpanData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.export.toExportTraceServiceRequest
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteReadPacket
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.util.toMap
import io.ktor.utils.io.ByteReadChannel
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest
import kotlin.test.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.io.readByteArray
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalApi::class)
class OtlpClientTest {

    private val logRecords = listOf(FakeReadableLogRecord())
    private val spans = listOf(FakeSpanData())
    private val baseUrl = "http://localhost:1234"

    private lateinit var client: OtlpClient
    private lateinit var server: MockEngine
    private lateinit var mockResponseStatus: HttpStatusCode
    private var serverDelayMs: Long = 0

    @Before
    fun setUp() {
        server = MockEngine { request ->
            if (serverDelayMs > 0) {
                delay(serverDelayMs)
            }
            respond(
                content = ByteReadChannel(""),
                status = mockResponseStatus
            )
        }
        val httpClient = createDefaultHttpClient(250, server)
        client = OtlpClient(baseUrl, httpClient = httpClient)
    }

    @Test
    fun testExportSingleLogSuccess() {
        sendAndAssertLogRequest(
            telemetry = logRecords,
            mockResponseStatus = HttpStatusCode.OK,
            expectedResponse = OtlpResponse.Success,
        )
    }

    @Test
    fun testExportMultiLogSuccess() {
        sendAndAssertLogRequest(
            telemetry = listOf(
                FakeReadableLogRecord(body = "a"),
                FakeReadableLogRecord(body = "b")
            ),
            mockResponseStatus = HttpStatusCode.OK,
            expectedResponse = OtlpResponse.Success,
        )
    }

    @Test
    fun testExportLogClientError() {
        sendAndAssertLogRequest(
            telemetry = logRecords,
            mockResponseStatus = HttpStatusCode.BadRequest,
            expectedResponse = OtlpResponse.ClientError(400, null)
        )
    }

    @Test
    fun testExportLogServerError() {
        sendAndAssertLogRequest(
            telemetry = logRecords,
            mockResponseStatus = HttpStatusCode.GatewayTimeout,
            expectedResponse = OtlpResponse.ServerError(504, null),
        )
    }

    @Test
    fun testExportSingleTraceSuccess() {
        sendAndAssertTraceRequest(
            telemetry = spans,
            mockResponseStatus = HttpStatusCode.OK,
            expectedResponse = OtlpResponse.Success,
        )
    }

    @Test
    fun testExportMultiTraceSuccess() {
        sendAndAssertTraceRequest(
            telemetry = listOf(
                FakeSpanData(name = "a"),
                FakeSpanData(name = "b"),
            ),
            mockResponseStatus = HttpStatusCode.OK,
            expectedResponse = OtlpResponse.Success,
        )
    }

    @Test
    fun testExportTraceClientError() {
        sendAndAssertTraceRequest(
            telemetry = spans,
            mockResponseStatus = HttpStatusCode.BadRequest,
            expectedResponse = OtlpResponse.ClientError(400, null)
        )
    }

    @Test
    fun testExportTraceServerError() {
        sendAndAssertTraceRequest(
            telemetry = spans,
            mockResponseStatus = HttpStatusCode.GatewayTimeout,
            expectedResponse = OtlpResponse.ServerError(504, null),
        )
    }

    @Test
    fun testExportLogClientTimeout() {
        serverDelayMs = 10000
        sendAndAssertLogRequest(
            telemetry = logRecords,
            mockResponseStatus = HttpStatusCode.OK,
            expectedResponse = OtlpResponse.Unknown,
        )
    }

    @Test
    fun testExportTraceClientTimeout() {
        serverDelayMs = 10000
        sendAndAssertTraceRequest(
            telemetry = spans,
            mockResponseStatus = HttpStatusCode.OK,
            expectedResponse = OtlpResponse.Unknown,
        )
    }

    private fun sendAndAssertLogRequest(
        telemetry: List<ReadableLogRecord>,
        mockResponseStatus: HttpStatusCode,
        expectedResponse: OtlpResponse
    ) {
        val bytes = sendAndAssertTelemetry(
            mockResponseStatus,
            expectedResponse,
            OtlpEndpoint.Logs
        ) {
            client.exportLogs(telemetry)
        } ?: return
        val protobuf = ExportLogsServiceRequest.parseFrom(bytes)
        assertEquals(telemetry.toExportLogsServiceRequest(), protobuf)
    }

    private fun sendAndAssertTraceRequest(
        telemetry: List<SpanData>,
        mockResponseStatus: HttpStatusCode,
        expectedResponse: OtlpResponse
    ) {
        val bytes = sendAndAssertTelemetry(
            mockResponseStatus,
            expectedResponse,
            OtlpEndpoint.Traces
        ) {
            client.exportTraces(telemetry)
        } ?: return
        val protobuf = ExportTraceServiceRequest.parseFrom(bytes)
        assertEquals(telemetry.toExportTraceServiceRequest(), protobuf)
    }

    private fun sendAndAssertTelemetry(
        mockResponseStatus: HttpStatusCode,
        expectedResponse: OtlpResponse,
        endpoint: OtlpEndpoint,
        exportAction: suspend () -> OtlpResponse,
    ): ByteArray? {
        this.mockResponseStatus = mockResponseStatus
        val response = runBlocking {
            exportAction()
        }
        assertEquals(expectedResponse.statusCode, response.statusCode)

        if (expectedResponse is OtlpResponse.Unknown) {
            return null
        }

        val request = server.requestHistory.single()
        assertEquals(HttpMethod.Post, request.method)
        assertEquals("$baseUrl/${endpoint.path}", request.url.toString())

        val contentType = checkNotNull(request.body.contentType)
        assertEquals("application/x-protobuf", contentType.toString())

        val headers = request.headers.toMap().mapValues { it.value.joinToString() }
        assertEquals("gzip,deflate", headers["Accept-Encoding"])

        val bytes = runBlocking {
            request.body.toByteReadPacket().readByteArray()
        }
        return bytes
    }
}
