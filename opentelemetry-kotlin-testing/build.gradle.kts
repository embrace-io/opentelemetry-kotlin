import io.embrace.otel.TargetPlatform

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("io.embrace.otel.build-logic")
}

buildLogic {
    containsPublicApi.set(false)
    targetPlatforms.set(listOf(TargetPlatform.JVM, TargetPlatform.ANDROID))
}

project.afterEvaluate {
    kotlin {
        sourceSets {
            val jvmMain by getting {
                dependencies {
                    api(project(":opentelemetry-kotlin"))
                    api(project(":compat-kotlin-to-official"))
                    api(project(":opentelemetry-java-typealiases"))

                    api(project.dependencies.platform(libs.opentelemetry.bom))
                    api(libs.opentelemetry.api)
                    implementation(libs.opentelemetry.sdk)

                    compileOnly(libs.junit4)
                    compileOnly(libs.junit5.api)
                }
            }
        }
    }
}
