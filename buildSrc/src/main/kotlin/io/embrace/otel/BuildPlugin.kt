package io.embrace.otel

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("buildLogic", BuildLogicExtension::class.java)
        project.configureKotlinExtension()
        project.configureAndroidExtension()
    }

    private fun Project.configureKotlinExtension() {
        val kotlin = project.extensions.getByType(KotlinMultiplatformExtension::class.java)

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
                        implementation("org.jetbrains.kotlin:kotlin-test")
                    }
                }
            }
        }
    }

    private fun Project.configureAndroidExtension() {
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
}
