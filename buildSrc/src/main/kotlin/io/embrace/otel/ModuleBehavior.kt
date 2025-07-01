package io.embrace.otel

import org.gradle.api.Project

/**
 * Whether a module contains interfaces that form part of the public API. This is determined by the presence
 * of the property `io.embrace.containsPublicApi` in the module. This enables explicit API mode and binary
 * compatibility validation.
 */
fun Project.containsPublicApi(): Boolean {
    return findProperty("io.embrace.containsPublicApi")?.toString()?.toBoolean() ?: true
}

/**
 * Whether a module consists of a compatibility layer for the opentelemetry-java SDK. This is determined by
 * the presence of the property `io.embrace.javaSdkCompatModule` in the module. We only need to build JVM
 * targets for these modules.
 */
fun Project.isJavaSdkCompatModule(): Boolean {
    return findProperty("io.embrace.javaSdkCompatModule")?.toString()?.toBoolean() ?: false
}
