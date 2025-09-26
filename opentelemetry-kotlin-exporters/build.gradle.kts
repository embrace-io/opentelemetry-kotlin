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
                implementation(project(":opentelemetry-kotlin-api"))
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.encoding)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-test-fakes"))
                implementation(libs.ktor.client.mock)
                implementation(libs.kotlin.test)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-exporters-jvm"))
                implementation(libs.ktor.client.okhttp)
                implementation(libs.protobuf.kotlin)
            }
        }
    }
}
