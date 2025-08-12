plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.otel.build-logic")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlin.serialization)
            }
        }
    }
}
