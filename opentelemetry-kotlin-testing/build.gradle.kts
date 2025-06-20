import io.embrace.otel.TargetPlatform

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

buildLogic {
    containsPublicApi.set(false)
    targetPlatforms.set(listOf(TargetPlatform.JVM, TargetPlatform.ANDROID))
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.testing"
}

project.afterEvaluate {
    kotlin {
        sourceSets {
            val jvmMain by getting {
                dependencies {
                    api(project(":opentelemetry-kotlin-api"))
                    api(project(":opentelemetry-kotlin-api-ext"))
                    implementation(project(":opentelemetry-kotlin-implementation"))
                    implementation(project(":opentelemetry-java-typealiases"))

                    api(project.dependencies.platform(libs.opentelemetry.bom))
                    api(libs.opentelemetry.api)
                    implementation(libs.opentelemetry.sdk)

                    api(project(":compat-kotlin-to-official"))

                    compileOnly(libs.junit4)
                    compileOnly(libs.junit5.api)
                }
            }
        }
    }
}
