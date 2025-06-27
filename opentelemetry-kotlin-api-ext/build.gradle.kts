plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.api.ext"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
            }
        }
    }
}
