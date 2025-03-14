package io.embrace.otel

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

fun Project.configureAndroid() {
    val android = extensions.getByType(LibraryExtension::class.java)

    android.apply {
        compileSdk = 35
        defaultConfig {
            minSdk = 21
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}
