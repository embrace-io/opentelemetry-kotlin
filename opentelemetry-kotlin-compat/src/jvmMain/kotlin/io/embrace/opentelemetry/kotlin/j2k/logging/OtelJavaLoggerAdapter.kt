package io.embrace.opentelemetry.kotlin.j2k.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.logging.Logger

@OptIn(ExperimentalApi::class)
internal class OtelJavaLoggerAdapter(private val impl: Logger) : OtelJavaLogger {

    override fun logRecordBuilder(): OtelJavaLogRecordBuilder =
        OtelJavaLogRecordBuilderAdapter(impl)
}
