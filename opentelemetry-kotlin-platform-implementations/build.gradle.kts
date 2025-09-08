plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":opentelemetry-kotlin-api"))
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
    }
}
