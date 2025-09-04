plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api-core"))
                api(project(":opentelemetry-kotlin-api-init"))
                api(project(":opentelemetry-kotlin-api-context"))
                api(project(":opentelemetry-kotlin-api-logging"))
                api(project(":opentelemetry-kotlin-api-tracing"))
                api(project(":opentelemetry-kotlin-api-factory"))
            }
        }
    }
}
