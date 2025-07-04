@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.j2k.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.j2k.bridge.OtelJavaLogRecordDataImpl
import io.embrace.opentelemetry.kotlin.j2k.bridge.attrsFromMap
import io.embrace.opentelemetry.kotlin.j2k.bridge.convertToOtelJava
import io.embrace.opentelemetry.kotlin.j2k.bridge.resourceFromMap
import io.embrace.opentelemetry.kotlin.k2j.logging.convertToOtelJava
import io.embrace.opentelemetry.kotlin.k2j.tracing.convertToOtelJava
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.api.logs.Severity
import io.opentelemetry.sdk.logs.data.Body
import io.opentelemetry.sdk.logs.data.LogRecordData

@OptIn(ExperimentalApi::class)
internal fun ReadableLogRecord.toLogRecordData(): LogRecordData {
    return OtelJavaLogRecordDataImpl(
        timestampNanos = timestamp ?: 0,
        observedTimestampNanos = observedTimestamp ?: 0,
        spanContextImpl = spanContext.convertToOtelJava(),
        severityTextImpl = severityText,
        severityImpl = severityNumber?.convertToOtelJava() ?: Severity.UNDEFINED_SEVERITY_NUMBER,
        bodyImpl = body?.let(Body::string) ?: Body.empty(),
        attributesImpl = attrsFromMap(attributes),
        resourceImpl = resource?.let(::resourceFromMap) ?: OtelJavaResource.empty(),
        scopeImpl = instrumentationScopeInfo?.convertToOtelJava()
            ?: OtelJavaInstrumentationScopeInfo.empty()
    )
}
