plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.otel.build-logic")
    alias(libs.plugins.kotlin.serialization)
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
