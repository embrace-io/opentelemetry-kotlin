package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.opentelemetry.sdk.common.CompletableResultCode

@OptIn(ExperimentalApi::class)
internal fun CompletableResultCode.toOperationResultCode(): OperationResultCode = when (this) {
    CompletableResultCode.ofSuccess() -> OperationResultCode.Success
    else -> OperationResultCode.Failure
}
