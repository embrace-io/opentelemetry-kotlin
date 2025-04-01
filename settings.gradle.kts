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
    ":opentelemetry-kotlin-api",
    ":opentelemetry-kotlin-api-ext",
    ":opentelemetry-kotlin-implementation",
    ":compat-shared",
    ":compat-official-to-kotlin",
    ":compat-kotlin-to-official",
    "examples:jvm-app-java-api",
    "examples:jvm-app-kotlin-api",
    "examples:android-app-java-api",
    "examples:android-app-kotlin-api",
    "examples:example-shared"
)
