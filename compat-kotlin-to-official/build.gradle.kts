plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("io.embrace.otel.build-logic")
}

group = "io.embrace.opentelemetry.kotlin"
version = "0.1.0"

buildLogic {
    containsPublicApi.set(true)
}

android {
    namespace = "io.embrace.opentelemetry.kotlin.compat.kotlin.to.official"
}

dependencies {
    implementation(project(":opentelemetry-kotlin-api"))

    api(project(":opentelemetry-kotlin-api"))
    api(platform(libs.opentelemetry.bom))
    api(libs.opentelemetry.api)
}
