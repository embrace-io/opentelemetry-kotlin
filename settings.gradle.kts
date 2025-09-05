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
    ":opentelemetry-kotlin-primitives",
    ":opentelemetry-kotlin-testing",
    ":opentelemetry-kotlin-test-fakes",
    ":opentelemetry-kotlin-integration-test",
    ":opentelemetry-kotlin-compat-bom",
    ":opentelemetry-java-typealiases",
    ":custom-detekt-rules",
    "examples:android-app",
    "examples:ios-app",
    "examples:js-app",
    "examples:jvm-app",
    "examples:example-common"
)
