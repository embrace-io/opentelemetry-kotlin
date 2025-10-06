plugins {
    kotlin("multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
                api(project(":opentelemetry-kotlin-platform-implementations"))
            }
        }
    }
}
