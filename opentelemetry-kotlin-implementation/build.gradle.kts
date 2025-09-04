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
                implementation(project(":opentelemetry-kotlin-api"))
                implementation(project(":opentelemetry-kotlin-api-ext"))
                implementation(project(":opentelemetry-kotlin-model"))
                implementation(project(":opentelemetry-kotlin-primitives"))
                implementation(project(":opentelemetry-kotlin-implementation-init"))
                implementation(project(":opentelemetry-kotlin-implementation-core"))
                implementation(project(":opentelemetry-kotlin-implementation-context"))
                implementation(project(":opentelemetry-kotlin-implementation-factory"))
                implementation(project(":opentelemetry-kotlin-implementation-logging"))
                implementation(project(":opentelemetry-kotlin-implementation-tracing"))
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
                implementation(project(":opentelemetry-kotlin-compat"))
                implementation(project(":opentelemetry-java-typealiases"))
                implementation(project.dependencies.platform(libs.opentelemetry.bom))
                implementation(libs.opentelemetry.api)
            }
        }
    }
}
