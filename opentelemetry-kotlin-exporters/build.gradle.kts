import de.undercouch.gradle.tasks.download.Download

plugins {
    kotlin("multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
    alias(libs.plugins.download)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-api"))
            }
        }
    }
}

// The release version of https://github.com/open-telemetry/opentelemetry-proto used to generate
// classes from protobuf definitions
val otelProtoVersion = "1.8.0"
val otelProtoRepoZip =
    "https://github.com/open-telemetry/opentelemetry-proto/archive/v${otelProtoVersion}.zip"

val downloadOtelProtoDefinitions by tasks.registering(Download::class) {
    src(otelProtoRepoZip)
    dest(layout.buildDirectory.file("opentelemetry-proto-${otelProtoVersion}.zip"))
    overwrite(false)
}

val extractOtelProtoDefinitions by tasks.registering(Copy::class) {
    dependsOn(downloadOtelProtoDefinitions)
    from(zipTree(downloadOtelProtoDefinitions.get().dest)) {
        include("opentelemetry-proto-$otelProtoVersion/opentelemetry/proto/**")
        includeEmptyDirs = false
    }
    into(layout.buildDirectory.dir("."))
}
