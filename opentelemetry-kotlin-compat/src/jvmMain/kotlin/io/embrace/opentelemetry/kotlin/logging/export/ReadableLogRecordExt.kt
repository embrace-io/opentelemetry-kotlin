@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.attributes.attrsFromMap
import io.embrace.opentelemetry.kotlin.attributes.resourceFromMap
import io.embrace.opentelemetry.kotlin.logging.OtelJavaLogRecordDataImpl
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.embrace.opentelemetry.kotlin.logging.toOtelJavaSeverityNumber
import io.embrace.opentelemetry.kotlin.scope.toOtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanContext
import io.opentelemetry.api.logs.Severity
import io.opentelemetry.sdk.logs.data.Body
import io.opentelemetry.sdk.logs.data.LogRecordData

@OptIn(ExperimentalApi::class)
internal fun ReadableLogRecord.toLogRecordData(): LogRecordData {
    return OtelJavaLogRecordDataImpl(
        timestampNanos = timestamp ?: 0,
        observedTimestampNanos = observedTimestamp ?: 0,
        spanContextImpl = spanContext.toOtelJavaSpanContext(),
        severityTextImpl = severityText,
        severityImpl = severityNumber?.toOtelJavaSeverityNumber() ?: Severity.UNDEFINED_SEVERITY_NUMBER,
        bodyImpl = body?.let(Body::string) ?: Body.empty(),
        attributesImpl = attrsFromMap(attributes),
        resourceImpl = resource?.let(::resourceFromMap) ?: OtelJavaResource.empty(),
        scopeImpl = instrumentationScopeInfo?.toOtelJavaInstrumentationScopeInfo()
            ?: OtelJavaInstrumentationScopeInfo.empty()
    )
}
