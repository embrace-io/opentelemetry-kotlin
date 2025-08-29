plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin"))
                implementation(project(":opentelemetry-kotlin-model"))
                implementation(project(":opentelemetry-java-typealiases"))
                implementation(project.dependencies.platform(libs.opentelemetry.bom))
                implementation(libs.opentelemetry.api)
                implementation(libs.opentelemetry.sdk)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-test-fakes"))
                implementation(project(":opentelemetry-kotlin-integration-test"))
            }
        }
    }
}
