package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk

/**
 * Constructs an [OpenTelemetry] instance that decorates the OpenTelemetry Java SDK. This will not use the Kotlin
 * implementation under the hood and will solely use the Java SDK implementation. This is useful if you have existing
 * OpenTelemetry Java SDK code that you don't want to rewrite, but still wish to use the Kotlin API.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.compatWithOtelJava(impl: OtelJavaOpenTelemetry): OpenTelemetry = OpenTelemetrySdk(impl)
