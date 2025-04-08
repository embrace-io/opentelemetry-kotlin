package io.embrace.opentelemetry.kotlin.k2j

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.internal.ImmutableSpanContext
import io.opentelemetry.api.logs.Logger
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.api.logs.Severity
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.api.trace.TraceFlags
import io.opentelemetry.api.trace.TraceState
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.TracerProvider
import io.opentelemetry.context.Context
import io.opentelemetry.context.ContextKey
import io.opentelemetry.sdk.common.Clock

internal typealias OtelJavaAttributes = Attributes
internal typealias OtelJavaAttributeKey<T> = AttributeKey<T>
internal typealias OtelJavaSpan = Span
internal typealias OtelJavaSpanContext = SpanContext
internal typealias OtelJavaTraceFlags = TraceFlags
internal typealias OtelJavaTraceState = TraceState
internal typealias OtelJavaSpanKind = SpanKind
internal typealias OtelJavaStatusCode = StatusCode
internal typealias OtelJavaContext = Context
internal typealias OtelJavaContextKey<T> = ContextKey<T>
internal typealias OtelJavaTracer = Tracer
internal typealias OtelJavaTracerProvider = TracerProvider
internal typealias OtelJavaClock = Clock
internal typealias OtelJavaImmutableSpanContext = ImmutableSpanContext
internal typealias OtelJavaOpenTelemetry = OpenTelemetry
internal typealias OtelJavaLoggerProvider = LoggerProvider
internal typealias OtelJavaLogger = Logger
internal typealias OtelJavaSeverity = Severity
