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
    ":opentelemetry-java-typealiases",
    ":custom-detekt-rules",
    "examples:jvm-app-java-api",
    "examples:jvm-app-kotlin-api",
    "examples:android-app-java-api",
    "examples:android-app-kotlin-api",
    "examples:example-shared"
)
