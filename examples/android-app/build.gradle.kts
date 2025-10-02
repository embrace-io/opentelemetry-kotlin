import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
}

dependencies {
    implementation(project(":examples:example-common"))
}

android {
    namespace = "io.embrace.opentelemetry.example.kotlin"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.embrace.opentelemetry.example.kotlin"
        minSdk = 21
        targetSdk = 36
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
}
