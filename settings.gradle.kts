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
    ":opentelemetry-kotlin-api-ext",
    ":opentelemetry-kotlin-noop",
    ":opentelemetry-kotlin-implementation",
    ":opentelemetry-kotlin-model",
    ":opentelemetry-kotlin-compat",
    ":opentelemetry-kotlin-platform-implementations",
    ":opentelemetry-kotlin-testing",
    ":opentelemetry-kotlin-test-fakes",
    ":opentelemetry-kotlin-integration-test",
    ":opentelemetry-kotlin-compat-bom",
    ":opentelemetry-kotlin-semconv",
    ":opentelemetry-kotlin-benchmark-android",
    ":opentelemetry-kotlin-benchmark-jvm",
    ":opentelemetry-java-typealiases",
    ":custom-detekt-rules",
    "examples:jvm-app",
    "examples:js-app",
    "examples:android-app",
    "examples:example-common"
)
