package io.embrace.opentelemetry.kotlin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.exclude
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

fun Project.configureKotlin(
    kotlin: KotlinMultiplatformExtension
) {
    kotlin.apply {
        jvmToolchain(8)
        compilerOptions.configureCompiler()

        js(IR) {
            nodejs()
            browser()
            binaries.library()
        }

        jvm {
            compilerOptions.configureCompiler()
        }
        // not officially supported yet, beyond confirming the target compiles.
        if (!project.isJavaSdkCompatModule()) {
            iosArm64 {
                compilerOptions.configureCompiler()
            }
        }

        sourceSets.apply {
            getByName("commonMain").apply {
                dependencies {
                    implementation(findLibrary("kotlin-exposed"))
                    // add dependencies here
                }
            }
            getByName("commonTest").apply {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-test") {
                        exclude(group = "junit")
                        exclude(group = "org.junit")
                    }
                }
            }
            getByName("jsMain")
        }
        compilerOptions {
            configureCompiler()
        }
    }
}

private fun KotlinCommonCompilerOptions.configureCompiler() {
    allWarningsAsErrors.set(true)
    apiVersion.set(KotlinVersion.KOTLIN_1_8)
    languageVersion.set(KotlinVersion.KOTLIN_1_8)
    freeCompilerArgs.add("-Xexpect-actual-classes")
}
