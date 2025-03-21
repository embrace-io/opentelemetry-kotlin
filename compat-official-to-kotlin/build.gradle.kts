plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(true)
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.compat.official.to.kotlin"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
                api(project.dependencies.platform(libs.opentelemetry.bom))
                api(libs.opentelemetry.api)
                implementation(libs.opentelemetry.sdk)
            }
        }
    }
}