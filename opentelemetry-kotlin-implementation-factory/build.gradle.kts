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
                implementation(project(":opentelemetry-kotlin-api-core"))
                implementation(project(":opentelemetry-kotlin-api-factory"))
                implementation(project(":opentelemetry-kotlin-implementation-context"))
                implementation(project(":opentelemetry-kotlin-implementation-logging"))
                implementation(project(":opentelemetry-kotlin-implementation-tracing"))
                implementation(project(":opentelemetry-kotlin-model"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-test-fakes"))
            }
        }
    }
}
