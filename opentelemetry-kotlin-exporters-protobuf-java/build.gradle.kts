import com.google.protobuf.gradle.id
import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
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
    implementation(project(":opentelemetry-kotlin-api-ext"))
    implementation(libs.protobuf.kotlin)
    testImplementation(project(":opentelemetry-kotlin-test-fakes"))
    testImplementation(libs.kotlin.test)
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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("build/generated/source/proto/main/kotlin")
        }
    }
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
}

// Disable Detekt tasks for generated code
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    enabled = false
}
