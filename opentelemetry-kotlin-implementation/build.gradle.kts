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
    containsPublicApi.set(true)
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
}