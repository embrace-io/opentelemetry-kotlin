import io.embrace.otel.TargetPlatform

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(true)
    targetPlatforms.set(listOf(TargetPlatform.JVM, TargetPlatform.ANDROID))
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.compat.official.to.kotlin"
}

project.afterEvaluate {
    kotlin {
        sourceSets {
            val jvmMain by getting {
                dependencies {
                    api(project(":opentelemetry-kotlin-api"))
                    api(project.dependencies.platform(libs.opentelemetry.bom))
                    api(libs.opentelemetry.api)
                }
            }
        }
    }
}
