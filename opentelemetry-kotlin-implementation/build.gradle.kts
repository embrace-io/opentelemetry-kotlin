import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(false)
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.implementation"
}

project.afterEvaluate {
    kotlin {
        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(project(":opentelemetry-kotlin-api"))
            }
        }
    }
}
