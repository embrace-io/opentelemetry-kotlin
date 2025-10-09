package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger

@OptIn(ExperimentalApi::class)
internal class OtelJavaLoggerAdapter(private val impl: Logger) : OtelJavaLogger {

    override fun logRecordBuilder(): OtelJavaLogRecordBuilder =
        OtelJavaLogRecordBuilderAdapter(impl)
}
