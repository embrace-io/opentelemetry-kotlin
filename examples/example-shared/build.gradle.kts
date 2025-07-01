plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
}

dependencies {
    api(project(":opentelemetry-kotlin"))
    implementation(project(":opentelemetry-kotlin-compat"))
    api(platform(libs.opentelemetry.bom))
    api(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
}
