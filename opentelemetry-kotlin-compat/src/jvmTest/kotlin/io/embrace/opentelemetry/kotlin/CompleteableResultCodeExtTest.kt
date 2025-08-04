package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.opentelemetry.sdk.common.CompletableResultCode
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class CompleteableResultCodeExtTest {

    @Test
    fun `test conversion`() {
        assertEquals(
            OperationResultCode.Success,
            CompletableResultCode.ofSuccess().toOperationResultCode()
        )
        assertEquals(
            OperationResultCode.Failure,
            CompletableResultCode.ofFailure().toOperationResultCode()
        )
    }
}
