plugins {
    kotlin("multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
                implementation(project(":opentelemetry-kotlin-platform-implementations"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-test-fakes"))
            }
        }
    }
}
