package io.embrace.otel

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Project
import org.gradle.kotlin.dsl.exclude
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

fun Project.configureKotlin(
    buildLogic: BuildLogicExtension,
    kotlin: KotlinMultiplatformExtension
) {
    kotlin.jvmToolchain(8)
    afterEvaluate {
        kotlin.apply {
            val platforms = buildLogic.targetPlatforms.get()

            if (platforms.contains(TargetPlatform.JVM)) {
                jvm {
                    compilerOptions.configureCompiler()
                }
            }
            if (platforms.contains(TargetPlatform.ANDROID)) {
                androidLibrary {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_1_8)
                        configureCompiler()
                    }
                }
            }
            // not officially supported yet, beyond confirming the target compiles.
            if (platforms.contains(TargetPlatform.IOS)) {
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
            }
            compilerOptions {
                configureCompiler()
            }
        }
    }
}

private fun KotlinCommonCompilerOptions.configureCompiler() {
    allWarningsAsErrors.set(true)
    apiVersion.set(KotlinVersion.KOTLIN_1_8)
    languageVersion.set(KotlinVersion.KOTLIN_1_8)
}
