plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.compat.shared"
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
                implementation(libs.kotlin.serialization)
                implementation("org.jetbrains.kotlin:kotlin-test") {
                    exclude(group = "junit")
                    exclude(group = "org.junit")
                }
            }
        }
    }
}
