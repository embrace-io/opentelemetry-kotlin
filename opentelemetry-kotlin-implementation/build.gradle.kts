plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-api"))
                implementation(project(":opentelemetry-kotlin-model"))
                implementation(project(":opentelemetry-kotlin-primitives"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-test-fakes"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-integration-test"))
            }
        }
    }
}
