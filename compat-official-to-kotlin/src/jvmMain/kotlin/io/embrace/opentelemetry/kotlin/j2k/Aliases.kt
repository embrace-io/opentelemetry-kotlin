@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.j2k

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

internal typealias OtelKotlinSpan = Span
internal typealias OtelKotlinSpanKind = SpanKind
internal typealias OtelKotlinTracer = Tracer
internal typealias OtelKotlinTracerProvider = TracerProvider
