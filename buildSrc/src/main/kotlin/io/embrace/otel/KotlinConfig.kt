package io.embrace.otel

import org.gradle.api.Project
import org.gradle.kotlin.dsl.exclude
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

fun Project.configureKotlin(
    buildLogic: BuildLogicExtension,
    kotlin: KotlinMultiplatformExtension
) {
    afterEvaluate {
        kotlin.apply {
            val platforms = buildLogic.targetPlatforms.get()

            if (platforms.contains(TargetPlatform.JVM)) {
                jvm()
            }
            if (platforms.contains(TargetPlatform.ANDROID)) {
                androidTarget {
                    publishLibraryVariants("release")
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_1_8)
                    }
                }
            }
            // not officially supported yet, beyond confirming the target compiles.
            if (platforms.contains(TargetPlatform.IOS)) {
                iosArm64()
            }

            sourceSets.apply {
                getByName("commonMain").apply {
                    dependencies {
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
                allWarningsAsErrors.set(true)
                apiVersion.set(KotlinVersion.KOTLIN_1_8)
                languageVersion.set(KotlinVersion.KOTLIN_1_8)
            }
        }
    }
}
