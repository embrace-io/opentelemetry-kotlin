package io.opentelemetry.api.trace

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey

internal val otelJavaSpanContextKey: OtelJavaContextKey<Span> = SpanContextKey.KEY
