package io.embrace.otel

import org.gradle.kotlin.dsl.exclude
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

fun configureKotlin(kotlin: KotlinMultiplatformExtension) {
    kotlin.apply {
        jvm()
        androidTarget {
            publishLibraryVariants("release")
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_1_8)
            }
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
