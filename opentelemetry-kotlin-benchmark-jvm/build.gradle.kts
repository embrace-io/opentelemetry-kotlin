import kotlinx.benchmark.gradle.benchmark
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlinx.benchmark)
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

kotlin {
    jvmToolchain(8)
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.benchmark.runtime)
                implementation(project(":opentelemetry-kotlin"))
                implementation(project(":opentelemetry-kotlin-implementation"))
            }
        }
        val jvmMain by getting {
            dependencies {

                implementation(project.dependencies.platform(libs.opentelemetry.bom))
                implementation(libs.opentelemetry.api)
                implementation(libs.opentelemetry.sdk)
                implementation(project(":opentelemetry-kotlin"))
                implementation(project(":opentelemetry-kotlin-compat"))
                implementation(project(":opentelemetry-kotlin-implementation"))
                implementation(project(":opentelemetry-java-typealiases"))
            }
        }
    }
}

benchmark {
    targets {
        register("jvm")
    }
}
