plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
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
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(project(":compat-shared"))
            }
        }
    }
}
