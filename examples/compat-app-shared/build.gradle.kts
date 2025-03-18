plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
}

dependencies {
    api(project(":opentelemetry-kotlin-api"))
    api(platform(libs.opentelemetry.bom))
    api(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
}
