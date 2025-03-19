import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    alias(libs.plugins.vanniktech.mavenPublish)
    id("io.embrace.otel.build-logic")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(true)
}

android {
    namespace = "io.embrace.opentelemetry.kotlin"
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "library", version.toString())

    pom {
        name = "Opentelemetry Kotlin"
        description = "An implementation of the OpenTelemetry specification as a Kotlin Multiplatform Library, developed by embrace.io."
        inceptionYear = "2025"
        url = "https://github.com/embrace-io/opentelemetry-kotlin"
    }
}
