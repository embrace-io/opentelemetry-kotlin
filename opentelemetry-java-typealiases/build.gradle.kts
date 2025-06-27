import io.embrace.otel.TargetPlatform

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(false)
    targetPlatforms.set(listOf(TargetPlatform.JVM, TargetPlatform.ANDROID))
}

project.afterEvaluate {
    kotlin {
        sourceSets {
            val jvmMain by getting {
                dependencies {
                    implementation(project.dependencies.platform(libs.opentelemetry.bom))
                    implementation(libs.opentelemetry.api)
                    implementation(libs.opentelemetry.sdk)
                }
            }
        }
    }
}