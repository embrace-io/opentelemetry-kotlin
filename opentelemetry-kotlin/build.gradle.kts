plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(true)
}

android {
    namespace = "io.embrace.opentelemetry.kotlin"
}

project.afterEvaluate {
    kotlin {
        sourceSets {
            val commonMain by getting {
                dependencies {
                    api(project(":opentelemetry-kotlin-api"))
                    api(project(":opentelemetry-kotlin-api-ext"))
                    api(project(":opentelemetry-kotlin-noop"))
                    api(project(":opentelemetry-kotlin-implementation"))
                }
            }
        }
    }
}
