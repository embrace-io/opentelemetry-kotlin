plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin"))
                implementation(project(":opentelemetry-java-typealiases"))
                implementation(project.dependencies.platform(libs.opentelemetry.bom))
                implementation(libs.opentelemetry.api)
                implementation(libs.opentelemetry.sdk)
                implementation(libs.kotlin.serialization)
            }
        }
    }
}
