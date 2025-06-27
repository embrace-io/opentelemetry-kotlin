package io.embrace.otel

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureAndroid(kotlin: KotlinMultiplatformExtension) {
    kotlin.apply {
        androidLibrary {
            val projectSuffix = project.name.replace("-", ".")
            namespace = "io.embrace.opentelemetry.kotlin.$projectSuffix"
            compileSdk = 36
            minSdk = 21
        }
        sourceSets.apply {
            androidMain {
                dependencies {
                    // Add Android-specific dependencies here
                }
            }
        }
    }
}
