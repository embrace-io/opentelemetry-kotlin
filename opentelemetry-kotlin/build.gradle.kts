plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
                api(project(":opentelemetry-kotlin-api-ext"))
                api(project(":opentelemetry-kotlin-noop"))
                implementation(project(":opentelemetry-kotlin-model"))
            }
        }
    }
}
