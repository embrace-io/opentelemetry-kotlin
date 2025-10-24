package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.createKeyValues
import io.embrace.opentelemetry.kotlin.export.conversion.toProtobuf
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest
import io.opentelemetry.proto.resource.v1.Resource
import io.opentelemetry.proto.trace.v1.ResourceSpans
import io.opentelemetry.proto.trace.v1.ScopeSpans

@OptIn(ExperimentalApi::class)
fun List<SpanData>.toProtobufByteArray() =
    ExportTraceServiceRequest.ADAPTER.encode(toExportTraceServiceRequest())

@OptIn(ExperimentalApi::class)
private fun List<SpanData>.toExportTraceServiceRequest(): ExportTraceServiceRequest =
    ExportTraceServiceRequest(toResourceSpan())

@OptIn(ExperimentalApi::class)
private fun List<SpanData>.toResourceSpan(): List<ResourceSpans> = map { it.toResourceSpan() }

@OptIn(ExperimentalApi::class)
private fun SpanData.toResourceSpan(): ResourceSpans = ResourceSpans(
    scope_spans = listOf(toScopedSpan()),
    resource = Resource(attributes = attributes.createKeyValues())
)

@OptIn(ExperimentalApi::class)
private fun SpanData.toScopedSpan(): ScopeSpans = ScopeSpans(
    spans = listOf(toProtobuf()),
    scope = instrumentationScopeInfo.toProtobuf(),
    schema_url = instrumentationScopeInfo.schemaUrl ?: ""
)

