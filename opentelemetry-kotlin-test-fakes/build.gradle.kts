plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
            }
        }
    }
}
