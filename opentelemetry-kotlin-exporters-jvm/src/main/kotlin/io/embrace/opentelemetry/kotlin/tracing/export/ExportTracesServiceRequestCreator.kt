package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.toProtobuf
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest
import io.opentelemetry.proto.collector.trace.v1.exportTraceServiceRequest
import io.opentelemetry.proto.trace.v1.ResourceSpans
import io.opentelemetry.proto.trace.v1.ScopeSpans
import io.opentelemetry.proto.trace.v1.resourceSpans
import io.opentelemetry.proto.trace.v1.scopeSpans

@OptIn(ExperimentalApi::class)
fun List<SpanData>.toExportTraceServiceRequest(): ExportTraceServiceRequest {
    return exportTraceServiceRequest {
        resourceSpans.addAll(map(::createResourceSpans))
    }
}

@OptIn(ExperimentalApi::class)
private fun createResourceSpans(span: SpanData): ResourceSpans = resourceSpans {
    scopeSpans.add(createScopeSpans(span))
    resource = span.resource.toProtobuf()
}

@OptIn(ExperimentalApi::class)
private fun createScopeSpans(span: SpanData): ScopeSpans = scopeSpans {
    spans.add(span.toProtobuf())
    scope = span.instrumentationScopeInfo.toProtobuf()
    span.instrumentationScopeInfo.schemaUrl?.let {
        schemaUrl = it
    }
}
