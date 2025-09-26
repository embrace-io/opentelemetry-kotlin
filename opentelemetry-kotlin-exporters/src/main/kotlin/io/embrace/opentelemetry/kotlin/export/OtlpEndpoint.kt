package io.embrace.opentelemetry.kotlin.export

enum class OtlpEndpoint(val path: String) {
    Logs("v1/logs"),
    Traces("v1/traces");
}
