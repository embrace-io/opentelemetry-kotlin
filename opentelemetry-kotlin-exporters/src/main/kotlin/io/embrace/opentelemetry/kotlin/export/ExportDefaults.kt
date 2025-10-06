package io.embrace.opentelemetry.kotlin.export

internal const val exportInitialDelayMs: Long = 30_000L // 30s
internal const val exportMaxAttemptIntervalMs: Long = 600000L // 10 mins
internal const val exportMaxAttempts: Int = 8 // maximum of 8 retries per export call
