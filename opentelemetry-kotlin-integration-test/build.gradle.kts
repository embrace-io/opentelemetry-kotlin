plugins {
    kotlin("multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-api"))
                implementation(project(":opentelemetry-kotlin-test-fakes"))
                implementation(libs.kotlin.serialization)
            }
        }
    }
}
