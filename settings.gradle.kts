pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "opentelemetry-kotlin"
include(
    ":opentelemetry-kotlin",
    ":opentelemetry-kotlin-api",
    ":opentelemetry-kotlin-api-tracing",
    ":opentelemetry-kotlin-api-logging",
    ":opentelemetry-kotlin-api-context",
    ":opentelemetry-kotlin-api-core",
    ":opentelemetry-kotlin-api-factory",
    ":opentelemetry-kotlin-api-init",
    ":opentelemetry-kotlin-api-ext",
    ":opentelemetry-kotlin-noop",
    ":opentelemetry-kotlin-implementation",
    ":opentelemetry-kotlin-implementation-context",
    ":opentelemetry-kotlin-implementation-core",
    ":opentelemetry-kotlin-implementation-factory",
    ":opentelemetry-kotlin-implementation-logging",
    ":opentelemetry-kotlin-implementation-tracing",
    ":opentelemetry-kotlin-implementation-init",
    ":opentelemetry-kotlin-model",
    ":opentelemetry-kotlin-compat",
    ":opentelemetry-kotlin-primitives",
    ":opentelemetry-kotlin-testing",
    ":opentelemetry-kotlin-test-fakes",
    ":opentelemetry-kotlin-integration-test",
    ":opentelemetry-kotlin-compat-bom",
    ":opentelemetry-java-typealiases",
    ":custom-detekt-rules",
    "examples:jvm-app",
    "examples:js-app",
    "examples:android-app",
    "examples:example-common"
)
