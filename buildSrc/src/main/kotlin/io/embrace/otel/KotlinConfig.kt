package io.embrace.otel

import com.android.build.api.dsl.androidLibrary
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

        jvm {
            compilerOptions.configureCompiler()
        }
        androidLibrary {
            namespace = "io.embrace.opentelemetry.kotlin.${project.name.replace("-", ".")}"
            compileSdk = 35
            minSdk = 21

            compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions.configureCompiler()
                }
            }
        }
        // not officially supported yet, beyond confirming the target compiles.
        if (!project.isJavaSdkCompatModule()) {
            iosArm64 {
                compilerOptions.configureCompiler()
            }
        }

        sourceSets.apply {
            androidMain {
                dependencies {
                    // Add Android-specific dependencies here
                }
            }
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
}
