plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin"))
                api(project(":opentelemetry-kotlin-compat"))
                api(project(":opentelemetry-java-typealiases"))

                api(project.dependencies.platform(libs.opentelemetry.bom))
                api(libs.opentelemetry.api)
                implementation(libs.opentelemetry.sdk)

                compileOnly(libs.junit4)
                compileOnly(libs.junit5.api)
            }
        }
    }
}
