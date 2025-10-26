package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.OtlpClient
import io.embrace.opentelemetry.kotlin.export.createDefaultHttpClient
import io.embrace.opentelemetry.kotlin.tracing.data.FakeSpanData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class OtlpHttpSpanExporterTest {

    private val spans = listOf(FakeSpanData())
    private val baseUrl = "http://localhost:1234"

    private lateinit var client: OtlpClient
    private lateinit var server: MockEngine
    private lateinit var mockResponseStatus: HttpStatusCode
    private lateinit var exporter: OtlpHttpSpanExporter
    private var serverDelayMs: Long = 0

    @BeforeTest
    fun setUp() {
        server = MockEngine {
            delay(serverDelayMs)
            respond(
                content = ByteReadChannel(""),
                status = mockResponseStatus
            )
        }
        val httpClient = createDefaultHttpClient(engine = server)
        client = OtlpClient(baseUrl, httpClient = httpClient)
        exporter = OtlpHttpSpanExporter(
            client,
            initialDelayMs = 3,
            maxAttemptIntervalMs = 5,
            maxAttempts = 3
        )
    }

    @Test
    fun testExportInitialSuccess() {
        mockResponseStatus = HttpStatusCode.OK
        val code = exporter.export(spans)
        assertEquals(OperationResultCode.Success, code)
        assertTelemetryExported(spans)
    }

    @Test
    fun testExportForceFlush() {
        val code = exporter.forceFlush()
        assertEquals(OperationResultCode.Success, code)
    }

    @Test
    fun testExportShutdown() {
        mockResponseStatus = HttpStatusCode.OK
        serverDelayMs = 1000
        val code = exporter.export(spans)
        assertEquals(OperationResultCode.Success, code)

        val shutdownCode = exporter.shutdown()
        assertEquals(OperationResultCode.Success, shutdownCode)

        runBlocking {
            withTimeout(10) {
                assertTrue(server.requestHistory.isEmpty())
            }
        }
    }

    @Test
    fun testExportRetryAttempts() {
        mockResponseStatus = HttpStatusCode.OK
        serverDelayMs = 2
        val code = exporter.export(spans)
        assertEquals(OperationResultCode.Success, code)
        assertTelemetryExported(spans)
    }

    private fun waitForExportedTelemetry(
        telemetrySize: Int = 1,
        timeoutMs: Long = 1000,
    ): List<HttpRequestData> =
        runBlocking {
            withTimeout(timeoutMs) {
                while (server.requestHistory.size < telemetrySize) {
                    delay(1L)
                }
            }
            val requests = server.requestHistory
            check(requests.size == telemetrySize) {
                "Expected 1 request, got ${requests.size}"
            }
            requests
        }

    private fun assertTelemetryExported(telemetry: List<SpanData>) {
        val requests = waitForExportedTelemetry()
        val request = requests.single()
        val bytes = runBlocking {
            request.body.toByteArray()
        }
        assertContentEquals(telemetry.toProtobufByteArray(), bytes)
    }
}
