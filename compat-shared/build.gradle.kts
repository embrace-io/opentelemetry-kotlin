import io.embrace.otel.TargetPlatform

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("io.embrace.otel.build-logic")
    alias(libs.plugins.kotlin.serialization)
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
                    api(project(":opentelemetry-kotlin-api"))
                    implementation(libs.kotlin.serialization)
                    implementation("org.jetbrains.kotlin:kotlin-test") {
                        exclude(group = "junit")
                        exclude(group = "org.junit")
                    }
                }
            }
        }
    }
}
