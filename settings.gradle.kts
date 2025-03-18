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
    "examples:jvm-app",
    "examples:jvm-compat-app",
    "examples:android-app",
    "examples:android-compat-app",
    "examples:app-shared",
    "examples:compat-app-shared",
)
