package io.embrace.opentelemetry.kotlin.k2j.logging

import io.embrace.opentelemetry.kotlin.k2j.OtelJavaSeverity
import io.embrace.opentelemetry.kotlin.logging.SeverityNumber
import io.opentelemetry.api.logs.Severity

internal fun SeverityNumber.convertToOtelJava(): OtelJavaSeverity = when (this) {
    SeverityNumber.UNKNOWN -> Severity.UNDEFINED_SEVERITY_NUMBER
    SeverityNumber.TRACE -> Severity.TRACE
    SeverityNumber.TRACE2 -> Severity.TRACE2
    SeverityNumber.TRACE3 -> Severity.TRACE3
    SeverityNumber.TRACE4 -> Severity.TRACE4
    SeverityNumber.DEBUG -> Severity.DEBUG
    SeverityNumber.DEBUG2 -> Severity.DEBUG2
    SeverityNumber.DEBUG3 -> Severity.DEBUG3
    SeverityNumber.DEBUG4 -> Severity.DEBUG4
    SeverityNumber.INFO -> Severity.INFO
    SeverityNumber.INFO2 -> Severity.INFO2
    SeverityNumber.INFO3 -> Severity.INFO3
    SeverityNumber.INFO4 -> Severity.INFO4
    SeverityNumber.WARN -> Severity.WARN
    SeverityNumber.WARN2 -> Severity.WARN2
    SeverityNumber.WARN3 -> Severity.WARN3
    SeverityNumber.WARN4 -> Severity.WARN4
    SeverityNumber.ERROR -> Severity.ERROR
    SeverityNumber.ERROR2 -> Severity.ERROR2
    SeverityNumber.ERROR3 -> Severity.ERROR3
    SeverityNumber.ERROR4 -> Severity.ERROR4
    SeverityNumber.FATAL -> Severity.FATAL
    SeverityNumber.FATAL2 -> Severity.FATAL2
    SeverityNumber.FATAL3 -> Severity.FATAL3
    SeverityNumber.FATAL4 -> Severity.FATAL4
}
