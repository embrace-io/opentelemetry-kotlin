import com.google.protobuf.gradle.id
import de.undercouch.gradle.tasks.download.Download

plugins {
    kotlin("jvm")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
    alias(libs.plugins.download)
    alias(libs.plugins.google.protobuf)
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

val updateOtelProtoDefinitions by tasks.registering(Copy::class) {
    val prefix = "opentelemetry-proto-$otelProtoVersion"

    dependsOn(downloadOtelProtoDefinitions)
    from(zipTree(downloadOtelProtoDefinitions.get().dest)) {
        include("opentelemetry-proto-$otelProtoVersion/opentelemetry/proto/**")
        eachFile {
            path = path.removePrefix(prefix)
        }
        exclude {
            !it.path.endsWith(".proto")
        }
        includeEmptyDirs = false
    }
    into(layout.projectDirectory.dir("src/main/proto"))
}

dependencies {
    implementation(project(":opentelemetry-kotlin-api"))
    implementation(libs.protobuf.kotlin)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.32.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins.id("kotlin")
        }
    }
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("build/generated/source/proto/main/kotlin")
        }
    }
}
