package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaCompletableResultCode
import io.embrace.opentelemetry.kotlin.export.OperationResultCode

@OptIn(ExperimentalApi::class)
internal fun OtelJavaCompletableResultCode.toOperationResultCode(): OperationResultCode = when (this) {
    OtelJavaCompletableResultCode.ofSuccess() -> OperationResultCode.Success
    else -> OperationResultCode.Failure
}
