package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalApi::class)
internal class TelemetryExporter<T>(
    private val initialDelayMs: Long,
    private val maxAttemptIntervalMs: Long,
    private val maxAttempts: Int,
    private val exportAction: suspend (telemetry: List<T>) -> OtlpResponse,
) : TelemetryCloseable {

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())

    /**
     * Exports telemetry via coroutines and uses exponential backoff when a failure
     * is encountered.
     */
    fun export(telemetry: List<T>): OperationResultCode {
        scope.launch {
            exportTelemetry(telemetry)
        }
        return Success
    }

    private suspend fun exportTelemetry(telemetry: List<T>) {
        var delayMs = initialDelayMs
        repeat(maxAttempts) {
            when (exportAction(telemetry)) {
                is OtlpResponse.Success -> {
                    return
                }

                is OtlpResponse.ClientError -> {
                    return
                }

                is OtlpResponse.ServerError, is OtlpResponse.Unknown -> {
                    delay(delayMs)
                    delayMs = (delayMs * 2).coerceAtMost(maxAttemptIntervalMs)
                }
            }
        }
    }

    override fun forceFlush(): OperationResultCode = Success

    override fun shutdown(): OperationResultCode {
        scope.cancel()
        return Success
    }
}
